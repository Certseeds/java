package backend.controller;

import backend.model.Token;
import backend.model.TokenDo;
import backend.model.User;
import backend.model.UserDO;
import backend.repository.TokenRepository;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    public UserController(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @PostMapping("/token")
    public TokenDo getToken(@RequestBody UserDO userDO) {
        System.out.println(userDO);
        if (userRepository.existsByUserNameAndAndPassWord(userDO.getUserName(), userDO.getPassWord())) {
            String uuidReturn = UUID.randomUUID().toString();
            System.out.println(uuidReturn);
            tokenRepository.save(new Token(uuidReturn, System.currentTimeMillis() / 1000));
            return new TokenDo(200, uuidReturn);
        } else {
            return new TokenDo(404, "");
        }
    }

    @PostMapping("/user")
    public int registerUser(@RequestBody UserDO userDO) {
        System.out.println(userDO);
        if (userRepository.existsByUserName(userDO.getUserName())) {
            return 403;
        } else {
            userRepository.save(new User(userDO));
            return 200;
        }
    }
}
