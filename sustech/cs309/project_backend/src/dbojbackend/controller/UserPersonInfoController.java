package dbojbackend.controller;

import dbojbackend.annotatin.needToken;
import dbojbackend.exception.globalException;
import dbojbackend.model.CommitResultType;
import dbojbackend.model.UserLevel;
import dbojbackend.model.response.StatisticsAnalysisResponse;
import dbojbackend.repository.CommitLogRepository;
import dbojbackend.repository.QuestionRepository;
import dbojbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UserPersonInfoController {
    private final UserRepository userRepository;
    private final CommitLogRepository commitLogRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public UserPersonInfoController(UserRepository userRepository, CommitLogRepository commitLogRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.commitLogRepository = commitLogRepository;
        this.questionRepository = questionRepository;
    }


    @GetMapping("/persona/{username}/{questionid}")
    @needToken(UserLevel.NORMAL_USER)
    public StatisticsAnalysisResponse commithistory(@PathVariable("username") String username, @PathVariable("questionid") Long qid) {
        if (userRepository.findByUserName(username).isEmpty()) {
            throw new globalException.NotFoundException("username Do not exist");
        } else if (questionRepository.findByProgramOrder(qid).isEmpty()) {
            throw new globalException.NotFoundException("ProgramOrder do not exist");
        }
        var userid = userRepository.findByUserName(username).get(0).getId();
        var commitLog = commitLogRepository.findByUserIdAndQuestionOrder(userid, qid);
        Long submission = (long) commitLog.size();
        var hashmap = new HashMap<CommitResultType, Long>();
        for (var x : CommitResultType.values()) {
            hashmap.put(x, commitLog.stream().filter(cl -> cl.getState() == x).count());
        }
        return new StatisticsAnalysisResponse(submission, hashmap);
    }

    @GetMapping("/persona/all/all")
    public StatisticsAnalysisResponse commithistoryOfAllUsers() {
        var commitLog = commitLogRepository.findAll();
        Long submission = (long) commitLog.size();
        var hashmap = new HashMap<CommitResultType, Long>();
        for (var x : CommitResultType.values()) {
            hashmap.put(x, commitLog.stream().filter(cl -> cl.getState() == x).count());
        }
        return new StatisticsAnalysisResponse(submission, hashmap);
    }
}
