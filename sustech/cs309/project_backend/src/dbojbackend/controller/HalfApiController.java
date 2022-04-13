package dbojbackend.controller;

import dbojbackend.exception.globalException;
import dbojbackend.model.UserLevel;
import dbojbackend.repository.UserRepository;
import dbojbackend.service.Token;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class HalfApiController {
    private final static String resetInformation = "reset forbidden";
    private final static String badupLevelInformation = "UpLevel Account NotFound";
    private final static String tooMuchTimeupLevelInformation = "UpLevel Account NotFound";
    @Resource
    UserRepository userRepository;
    @Resource
    Token tokenResource;

    @GetMapping("/login/reset")
    public String HalfAPIreset(@RequestParam String token) {
        if (null == tokenResource.checkToken(token)) {
            throw new globalException.ForbiddenException(resetInformation);
        }
        // do not nned to check user exist , checkToken had done that
        String newPassword = tokenResource.gettokenpassword(token);
        userRepository.updatePasswordByUsername(tokenResource.gettokenUserName(token), newPassword);
        return "redirect:http://localhost:8080/";
    }

    @Modifying
    @GetMapping("/upLevel")
    public String halfApiUpLevel(@RequestParam String token) {
        if (null == tokenResource.checkToken(token)) {
            throw new globalException.NotFoundException(badupLevelInformation);
        }
        if (UserLevel.CREATE != tokenResource.checkToken(token)) {
            throw new globalException.ForbiddenException(tooMuchTimeupLevelInformation);
        }
        var user = userRepository.findByUserName(tokenResource.gettokenUserName(token)).get(0);
        userRepository.updateLevelById(user.getId(), UserLevel.NORMAL_USER);
        return "redirect:http://localhost:8080/";
    }

}
