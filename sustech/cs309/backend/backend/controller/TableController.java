package backend.controller;

import backend.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class TableController {
    private static final int DAY_SECOND = 24 * 60 * 60;
    private final TokenRepository tokenRepository;

    @Autowired
    public TableController(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/table")
    public String getCorrectOrNot(@RequestParam Map<String, String> params, Model model) {
        String tokenId = params.get("tokenId");
        System.out.println(tokenId);
        System.out.println(System.currentTimeMillis() / 1000);
        if (tokenRepository.existsByTokenId(tokenId) &&
                tokenRepository.findFirstByTokenId(tokenId).getTokenSecond() -
                        System.currentTimeMillis() / 1000 < DAY_SECOND) {
            return "table";
        } else {
            return "NotFound";
        }
    }
}