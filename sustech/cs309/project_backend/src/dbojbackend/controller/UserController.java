package dbojbackend.controller;


import dbojbackend.exception.globalException;
import dbojbackend.model.CommitResultType;
import dbojbackend.model.response.userdate_UsernameResponse;
import dbojbackend.repository.CommitLogRepository;
import dbojbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final CommitLogRepository commitLogRepository;
    @Autowired
    public UserController(UserRepository userRepository, CommitLogRepository commitLogRepository) {
        this.userRepository = userRepository;
        this.commitLogRepository = commitLogRepository;
    }

    @GetMapping("/user")
    public String user_username(@RequestParam String username) {
        log.info("Enter user_username " + username);
        return username;
    }

    @GetMapping("/userdata")
    public userdate_UsernameResponse userdate_username(@RequestParam("username") String username) {
        log.info("enter userdata_username " + username);
        var temp = userRepository.findByUserName(username);
        if (temp.isEmpty()) {
            throw new globalException.NotFoundException("User do not exist");
        }
        var userid = temp.get(0).getId();
        var commitlog = commitLogRepository.findByUserId(userid);
        var AC = commitlog.stream().filter(x -> x.getState() == CommitResultType.AC).count();
        var Submin = commitlog.size();
        var will_return = new userdate_UsernameResponse(username, "" + AC, Submin + "", username, AC * 100 + "", "", 0, "");
        log.info(will_return.toString());
        return will_return;
    }

    @GetMapping("/contestratingchange")
    public userdate_UsernameResponse contestratingchange_username(@RequestParam("username") String username) {
        log.info("enter userdata_username " + username);
        var temp = userRepository.findByUserName(username);
        if (temp.isEmpty()) {
            throw new globalException.NotFoundException("User do not exist");
        }
        var userid = temp.get(0).getId();
        var commitlog = commitLogRepository.findByUserId(userid);
        var AC = commitlog.stream().filter(x -> x.getState() == CommitResultType.AC).count();
        var Submin = commitlog.size();
        var will_return = new userdate_UsernameResponse(username, "" + AC, Submin + "", username, AC * 100 + "", "", 0, "");
        log.info(will_return.toString());
        return will_return;
    }
}
