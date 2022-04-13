package dbojbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbojbackend.annotatin.needToken;
import dbojbackend.exception.globalException;
import dbojbackend.model.CommitResultType;
import dbojbackend.model.SqlLanguage;
import dbojbackend.model.State;
import dbojbackend.model.UserLevel;
import dbojbackend.model.data.*;
import dbojbackend.model.request.*;
import dbojbackend.model.response.CommitQueryResponse;
import dbojbackend.model.response.JudgeSystemResultResponse;
import dbojbackend.model.response.judgestatusCodesResponse;
import dbojbackend.repository.*;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/commit")
public class CommitController {
    @Resource
    UserRepository userRepository;
    @Resource
    CommitLogRepository commitLogRepository;

    @Resource
    CommitResultRepository commitResultRepository;
    @Resource
    QuestionRepository questionRepository;

    @Resource
    QuestionDetailRepository questionDetailRepository;

    @Resource
    QuestionBuildRepository questionBuildRepository;

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/history")
    @needToken(UserLevel.NORMAL_USER)
    public List<CommitLog> commithistory(@RequestBody usernameWarpperRequest usernameWarpper) {
        var username = usernameWarpper.getUsername();
        var users = userRepository.findByUserName(username);
        if (users.isEmpty()) {
            throw new globalException.NotFoundException("Wrong UserName");
        }
        var user = users.get(0);
        return commitLogRepository.findByUserId(user.getId());
    }

    @PostMapping("/history/{order}")
    @needToken(UserLevel.NORMAL_USER)
    public List<CommitLog> commithistory(@PathVariable(value = "order") Long questionOrder,
                                         @RequestBody usernameWarpperRequest usernameWarpper) {
        var username = usernameWarpper.getUsername();
        var users = userRepository.findByUserName(username);
        if (users.isEmpty()) {
            throw new globalException.NotFoundException("Wrong UserName");
        }
        var user = users.get(0);
        return commitLogRepository.findByUserIdAndQuestionOrder(user.getId(), questionOrder);
    }

    @PostMapping("/judgestatuscode/{order}")
    @needToken(UserLevel.NORMAL_USER)
    public judgestatusCodesResponse judgestatusCodes(@PathVariable(value = "order") Long questionOrder,
                                                     @RequestBody judgeStatusCodesRequest judgeStatusCodesrequest) {
        var username = judgeStatusCodesrequest.getUsername();
        var users = userRepository.findByUserName(username);
        if (users.isEmpty()) {
            throw new globalException.NotFoundException("Wrong UserName");
        }
        var questionId = questionOrder;
        var listCommitResult = commitResultRepository.findByCommitLogId(questionId);
        var listCommitLog = commitLogRepository.findByCommitLogId(questionId);
        if (listCommitResult.isEmpty() || listCommitLog.isEmpty()) {
            throw new globalException.NotFoundException("Do not exist CommitResult");
        }
        var commitResult = listCommitResult.get(0);
        var commitLog = listCommitLog.get(0);
        var judgeStatusCoderesponse = new judgestatusCodesResponse(
                questionId, commitResult.getCputime(), commitResult.getRamsize(), commitLog.getCommitCode()
        );
        return judgeStatusCoderesponse;
    }


    @Modifying
    @PostMapping("/query/{quesid}")
    @needToken(UserLevel.NORMAL_USER)
    public CommitQueryResponse commitQuery(@PathVariable("quesid") Long useless
            , @RequestBody CommitQuery cqo) {
        var questionList = questionRepository.findByProgramOrder(cqo.getQuestionId());
        if (questionList.isEmpty()) {
            throw new globalException.NotFoundException("program number not found");
        }
        var questionCommit = questionList.get(0);
        var questionDetailList = questionDetailRepository.findByProgramOrderAndLanguage(cqo.getQuestionId(), cqo.getLanguage());
        if (questionDetailList.isEmpty()) {
            throw new globalException.NotFoundException("program number not found");
        }
        var questionDetail = questionDetailList.get(0);

        var questionBuildList = questionBuildRepository.findByProgramOrderAndLanguageAndTableOrder(
                cqo.getQuestionId(),
                cqo.getLanguage(),
                (long) cqo.getTestOrRun());
        if (questionBuildList.isEmpty()) {
            throw new globalException.NotFoundException("program number not found");
        }
        var questionbuild = questionBuildList.get(0);
        JudgeRequest correct = new JudgeRequest();
        JudgeRequest commit = new JudgeRequest();
        correct.setLanguage(cqo.getLanguage());
        commit.setLanguage(cqo.getLanguage());
        correct.setLimitTime(questionDetail.getCputime());
        commit.setLimitTime(questionDetail.getCputime());
        correct.setLimitMemory(questionDetail.getMemory());
        commit.setLimitMemory(questionDetail.getMemory());
        correct.setCreateTable(questionbuild.getBuildScript());
        //.replace('\n', ' '));
        commit.setCreateTable(questionbuild.getBuildScript());
        //.replace('\n', ' '));
        correct.setSearchTable(questionDetail.getCorrectScript());
        commit.setSearchTable(cqo.getCommitCode());
        String url = "http://127.0.0.1:11223";
        String[] correctResults = new String[2];
        List<Thread> threads = new ArrayList<>();
        threads.add(new Thread(() -> {
            correctResults[0] = restTemplate.postForObject(url, correct, String.class);
        }));
        threads.add(new Thread(() -> {
            correctResults[1] = restTemplate.postForObject(url, commit, String.class);
        }));
        try {
            threads.get(0).start();
            threads.get(1).start();
            threads.get(0).join();
            threads.get(1).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(correctResults[0]);
        System.out.println(correctResults[1]);
        ObjectMapper mapper = new ObjectMapper();
        JudgeSystemResultResponse result1 = null;
        JudgeSystemResultResponse result2 = null;
        try {
            result1 = mapper.readValue(correctResults[0], JudgeSystemResultResponse.class);
            result2 = mapper.readValue(correctResults[1], JudgeSystemResultResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        CommitLog commitLog;
        Long userId = userRepository.findByUserName(cqo.getUsername()).get(0).getId();

        assert result1 != null;
        assert result2 != null;
        commitLog = new CommitLog(userId, cqo.getQuestionId(), cqo.getCommitCode(), cqo.getLanguage(), CommitResultType.WA);
        System.out.println(result1.getData().equals(result2.getData()));
        if (result2.getState() != 0) {
            commitLog.setState(CommitResultType.values()[result2.getState()]);
        } else if (result2.getTime() > questionDetail.getCputime() * 1000) {// s -> ms
            commitLog.setState(CommitResultType.TLE);
        } else if (result2.getMemory() > questionDetail.getMemory()) {
            commitLog.setState(CommitResultType.MLE);
        } else if (Objects.equals(result1.getData(), result2.getData())) {
            commitLog.setState(CommitResultType.AC);
        }
        if (commitLog.getState() == CommitResultType.TLE) {
            result2.setTime(questionDetail.getCputime().intValue() * 1000 + 1);
        } else if (commitLog.getState() == CommitResultType.MLE) {
            result2.setMemory(questionDetail.getMemory().intValue() + 1);
        }
        commitLogRepository.save(commitLog);
        var commitLogMax = commitLogRepository.findAll()
                .stream()
                .max(Comparator.comparing(CommitLog::getCommitLogId))
                .get().getCommitLogId();
        CommitResult commitResult = new CommitResult(commitLogMax,
                0L,
                "",
                result2.getTime().longValue(),
                result2.getMemory().longValue()
        );
        commitResultRepository.save(commitResult);
        return new CommitQueryResponse(cqo.getQuestionId(), commitLog);
    }


    @Modifying
    @PostMapping("/create")
    @needToken(UserLevel.ADMIN)
    public Question CommitCreateQuestion(@RequestBody CommitCreateQuestion ccq) {
        var question = new Question(ccq.getQuestionTitle(), ccq.getDescription(), ccq.getDeadline());
        try {
            questionRepository.save(question);
        } catch (Exception e) {
            throw new globalException.ForbiddenException("unknown error ");
        }
        var maxQuestionsOrderInTheDataBase = questionRepository
                .findAll()
                .stream()
                .max(Comparator.comparing(Question::getProgramOrder))
                .get();
        return maxQuestionsOrderInTheDataBase;
    }

    @Modifying
    @PostMapping("/update")
    @needToken(UserLevel.ADMIN)
    public State commitUpdate(@RequestBody CommitUpdateQuestion request) {
        Long id = request.getProgramOrder();
        if (questionRepository.findByProgramOrder(id).isEmpty()) {
            throw new globalException.NotFoundException("can not update to null");
        }
        questionDetailRepository.save(new QuestionDetail(
                request.getProgramOrder(), request.getCorrectCommand(), request.getLanguage(),
                request.getCputime(), request.getMomory()
        ));
        try {
            for (var s : request.getGroup()) {
                QuestionBuild questionBuild = new QuestionBuild();
                questionBuild.setProgramOrder(request.getProgramOrder());
                questionBuild.setBuildScript(s);
                questionBuild.setLanguage(request.getLanguage());
                questionBuild.setTableOrder(0L);
                questionBuildRepository.save(questionBuild);
            }
            return State.SUCCESS;
        } catch (Exception e) {
            throw new globalException.ForbiddenException("some error happen during update to questionBuild");
        }
    }

    @GetMapping("/update")
    @needToken(UserLevel.ADMIN)
    public CommitUpdateQuestion commitUpdateGetInformation(@RequestBody CommitUpdateQuestion request) {
        // this one use to sync the informations
        Long programOrder = request.getProgramOrder();
        SqlLanguage language = request.getLanguage();
        var details = questionDetailRepository.findByProgramOrderAndLanguage(programOrder, language);
        if (details.isEmpty()) {
            return request;
        }
        String correctCommand = details.get(0).getCorrectScript();
        request.setCorrectCommand(correctCommand);
        var builds = questionBuildRepository.findByProgramOrderAndLanguage(programOrder, language);
        if (builds.isEmpty()) {
            return request;
        }
        for (var parameter : builds) {
            request.getGroup().add(parameter.getBuildScript());
        }
        return request;
    }

    @Modifying
    @DeleteMapping("/delete")
    @needToken(UserLevel.ADMIN)
    public State commitDelete(@RequestBody CommitDeleteRequest request) {
        try {
            questionDetailRepository.deleteById(request.getQuestionOrder());
            questionBuildRepository.deleteByProgramOrder(request.getQuestionOrder());
            questionRepository.deleteById(request.getQuestionOrder());
            return State.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw new globalException.ForbiddenException("Error in delete question");
        }
    }


}