package dbojbackend.controller;

import dbojbackend.model.response.Judgestatus;
import dbojbackend.repository.CommitLogRepository;
import dbojbackend.repository.CommitResultRepository;
import dbojbackend.repository.QuestionRepository;
import dbojbackend.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JudgeController {
    private final Map<Long, String> languageMap = new HashMap<>() {
        @Serial
        private static final long serialVersionUID = -6626340725699254149L;
        {
            put(0L, "MYSQL");
            put(1L, "SQLITE");
            put(2L, "POSTGRESQL");
        }
    };
    @Resource
    UserRepository userRepository;
    @Resource
    CommitLogRepository commitLogRepository;
    @Resource
    CommitResultRepository commitResultRepository;
    @Resource
    QuestionRepository questionRepository;

    @GetMapping("/judgestatus")
    public List<Judgestatus> judgestatus(@RequestParam(value = "user", required = false) String user,
                                         @RequestParam(value = "problem", required = false) Long problem,
                                         @RequestParam(value = "language", required = false) String language,
                                         @RequestParam(value = "problemtitle", required = false) String problemtitle) {
        user = user.equals("") ? null : user;
        language = language.equals("") ? null : language;
        List<Judgestatus> judgestatusResponse = new ArrayList<>();
        var logs = commitLogRepository.findAll();
        for (var commitLog : logs) {
            var userName = userRepository.findByid(commitLog.getUserId()).get(0).getUserName();
            if (user != null && !userName.equals(user)) continue;
            var lan = languageMap.get(commitLog.getLanguage().getValue());
            if (language != null && !lan.equals(language.toUpperCase())) continue;
            var prob = questionRepository.findByProgramOrder(commitLog.getQuestionOrder()).get(0);
            if (problem != null && !prob.getProgramOrder().equals(problem)) continue;
            if (problemtitle != null && !prob.getName().equals(problemtitle)) continue;
            var results = commitResultRepository.findByCommitLogId(commitLog.getCommitLogId());
            if (results.isEmpty()) {
                continue;
            }
            for (var r : results) {
                Judgestatus temp = new Judgestatus();
                temp.setUser(userName);//user name
                temp.setProblem(prob.getName()); //question name
                temp.setTable(r.getTableOrder().toString());//table order
                temp.setResult(commitLog.getState());//result
                temp.setTime(r.getCputime());
                temp.setMemory(r.getRamsize());
                temp.setLanguage(lan);
                temp.setLength(commitLog.getCommitCode().length());
                judgestatusResponse.add(temp);
            }
        }
        return judgestatusResponse;
    }

}