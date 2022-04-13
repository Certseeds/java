package dbojbackend.controller;

import dbojbackend.annotatin.needToken;
import dbojbackend.exception.globalException;
import dbojbackend.model.CommitResultType;
import dbojbackend.model.SqlLanguage;
import dbojbackend.model.UserLevel;
import dbojbackend.model.response.infoApiResponse;
import dbojbackend.model.response.infoStatusResponse;
import dbojbackend.model.response.userListResponse;
import dbojbackend.repository.CommitLogRepository;
import dbojbackend.repository.CommitResultRepository;
import dbojbackend.repository.QuestionDetailRepository;
import dbojbackend.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class adminController {
    @Resource
    UserRepository userRepository;

    @Resource
    CommitLogRepository commitLogRepository;

    @Resource
    CommitResultRepository commitResultRepository;

    @Resource
    QuestionDetailRepository questionDetailRepository;

    @GetMapping("/info/status/{questionId}")
    public infoStatusResponse info_status_questionid(@PathVariable(value = "questionId") Long qid) {
        var listCommitLogs = commitLogRepository.findByQuestionOrder(qid);
        if (listCommitLogs.isEmpty()) {
            throw new globalException.NotFoundException("Wrong Question Id,No Information");
        }
        var hashmap = new HashMap<CommitResultType, Long>();
        for (var x : CommitResultType.values()) {
            hashmap.put(x, listCommitLogs.stream().filter(cl -> cl.getState() == x).count());
        }
        return new infoStatusResponse(qid, hashmap);

    }

    @GetMapping("/info/time/{questionId}")
    public infoApiResponse info_time_questionid(@PathVariable(value = "questionId") Long qid) {
        var listCommitLogs = commitLogRepository.findByQuestionOrder(qid);
        if (listCommitLogs.isEmpty()) {
            throw new globalException.NotFoundException("Wrong Question Id,No Information");
        }
        var listQuestionDetails1 = questionDetailRepository.findByProgramOrderAndLanguage(qid, SqlLanguage.MYSQL);
        var maxRunTime = listQuestionDetails1.get(0).getCputime() * 1000;
        var LongValueList = new ArrayList<Long>();
        for (var commitlog : listCommitLogs) {
            var commitLogId = commitlog.getCommitLogId();
            for (var commitResults : commitResultRepository.findByCommitLogId(commitLogId)) {
                LongValueList.add(commitResults.getCputime());
            }
        }
        return new infoApiResponse(qid, maxRunTime, LongValueList);

    }

    @GetMapping("/info/memory/{questionId}")
    public infoApiResponse info_memory_questionid(@PathVariable(value = "questionId") Long qid) {
        var listCommitLogs = commitLogRepository.findByQuestionOrder(qid);
        if (listCommitLogs.isEmpty()) {
            throw new globalException.NotFoundException("Wrong Question Id,No Information");
        }
        var listQuestionDetails1 = questionDetailRepository.findByProgramOrderAndLanguage(qid, SqlLanguage.MYSQL);
        var maxRunTime = listQuestionDetails1.get(0).getMemory() / 1000; // KB to MBl
        var LongValueList = new ArrayList<Long>();
        for (var commitlog : listCommitLogs) {
            var commitLogId = commitlog.getCommitLogId();
            for (var commitResults : commitResultRepository.findByCommitLogId(commitLogId)) {
                LongValueList.add(commitResults.getRamsize() / 1000);
            }
        }
        return new infoApiResponse(qid, maxRunTime, LongValueList);
    }

    @GetMapping("/userList")
    @needToken(UserLevel.ADMIN)
    public List<userListResponse> userList() {
        var ListUserListResponse = new ArrayList<userListResponse>();
        var users = userRepository.findAll();
        for (var user : users) {
            var listUser = new userListResponse(user.getId(), user.getUserName(), user.getEmail());
            ListUserListResponse.add(listUser);
        }
        return ListUserListResponse;
    }
}
