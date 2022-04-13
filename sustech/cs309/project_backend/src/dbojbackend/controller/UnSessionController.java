package dbojbackend.controller;

import dbojbackend.exception.globalException;
import dbojbackend.model.CommitResultType;
import dbojbackend.model.SqlLanguage;
import dbojbackend.model.data.Question;
import dbojbackend.model.response.QuestionWithAcNumberAndSubmission;
import dbojbackend.model.response.TimaAndMemoryResponse;
import dbojbackend.model.response.infoResponse;
import dbojbackend.repository.CommitLogRepository;
import dbojbackend.repository.CommitResultRepository;
import dbojbackend.repository.QuestionDetailRepository;
import dbojbackend.repository.QuestionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@RestController
public class UnSessionController {
    static Supplier<ConcurrentHashMap<String, SqlLanguage>> func = () -> {
        var result = new ConcurrentHashMap<String, SqlLanguage>();
        result.put("MySQL", SqlLanguage.MYSQL);
        result.put("SQLite", SqlLanguage.SQLITE);
        result.put("PostgreSQL", SqlLanguage.POSTGRESQL);
        return result;
    };
    private static final ConcurrentHashMap<String, SqlLanguage> language = func.get();
    @Resource
    QuestionRepository questionRepository;
    @Resource
    CommitLogRepository commitLogRepository;
    @Resource
    CommitResultRepository commitResultRepository;
    @Resource
    QuestionDetailRepository questionDetailRepository;

    @GetMapping("/problems/info/{questionid}/{language}")
    public TimaAndMemoryResponse problemsTimeAndMemoryGet(@PathVariable("questionid") Long qid, @PathVariable("language") String str) {
        if (!language.containsKey(str)) {
            throw new globalException.NotFoundException("SQL language do not support");
        } else if (questionRepository.findByProgramOrder(qid).isEmpty()) {
            throw new globalException.NotFoundException("Program Order not Found!");
        }
        var valueOfLanguage = language.get(str);
        var details = questionDetailRepository.findByProgramOrderAndLanguage(qid, valueOfLanguage);
        if (details.isEmpty()) {
            throw new globalException.NotFoundException("Program Order And Language do not match!!");
        }
        var detail = details.get(0);
        return new TimaAndMemoryResponse(detail.getCputime(), detail.getMemory());
    }

    @GetMapping("/problems/{question_order}")
    public Question problemsDetail(@PathVariable("question_order") String question_order) {
        List<Question> questions = questionRepository.findByProgramOrder(Long.parseLong(question_order));
        if (questions.isEmpty()) {
            throw new globalException.NotFoundException("Illegal question order");
        }
        return questions.get(0);
    }

    @GetMapping("/problems")
    public List<QuestionWithAcNumberAndSubmission> problemsList() {
        var will_return = new ArrayList<QuestionWithAcNumberAndSubmission>();
        var tempquestions = questionRepository.findAll();
        for (var question : tempquestions) {
            var commitLogs = commitLogRepository.findByQuestionOrder(question.getProgramOrder());
            var acNumber = commitLogs.stream().filter(x -> x.getState() == CommitResultType.AC).count();
            var submission = commitLogs.size();
            will_return.add(new QuestionWithAcNumberAndSubmission(question, acNumber, (long) submission));
        }
        return will_return;
    }

    @GetMapping("/info")
    public List<infoResponse> getAllInfo() {
        var temp = commitLogRepository.findAll();
        var temp2 = commitResultRepository.findAll();
        var questionOrderToinfo = new ConcurrentHashMap<Long, infoResponse>(16);
        var commitIdToiQuestionOrder = new ConcurrentHashMap<Long, Long>(2 << 10);
        for (var parameter : temp) {
            commitIdToiQuestionOrder.put(parameter.getCommitLogId(), parameter.getQuestionOrder());
            if (!questionOrderToinfo.containsKey(parameter.getQuestionOrder())) {
                questionOrderToinfo.put(parameter.getQuestionOrder(), new infoResponse(parameter.getQuestionOrder()));
            }
            questionOrderToinfo.get(parameter.getQuestionOrder()).getTypeValues()[parameter.getState().getOrder()]++;
        }
        for (var parameter : temp2) {
            questionOrderToinfo.get(commitIdToiQuestionOrder.get(parameter.getCommitLogId())).getCputime().add(parameter.getCputime());
            questionOrderToinfo.get(commitIdToiQuestionOrder.get(parameter.getCommitLogId())).getMemsize().add(parameter.getRamsize());
        }
        return new ArrayList<>(questionOrderToinfo.values());
    }

    @GetMapping("/info/{questionOrder}")
    public infoResponse getAllInfo(@PathVariable(value = "questionOrder") Long order) {
        var will_return = new infoResponse(order);
        var commitLogs = commitLogRepository.findByQuestionOrder(order);
        var commitResults = commitResultRepository.findAll();
        var commitLogIdSet = new HashSet<Long>();
        for (var commitLog : commitLogs) {
            commitLogIdSet.add(commitLog.getCommitLogId());
            will_return.getTypeValues()[commitLog.getState().getOrder()] += 1;
        }
        for (var parameter : commitResults) {
            if (commitLogIdSet.contains(parameter.getCommitLogId())) {
                will_return.getCputime().add(parameter.getCputime());
                will_return.getMemsize().add(parameter.getRamsize());
            }
        }
        return will_return;
    }

}
