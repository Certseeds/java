// SPDX-License-Identifier: AGPL-3.0-or-later
package backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RegisterController {


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("name", "");
        return "login";
    }

    @GetMapping("/login/{name}")
    public String login(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }
}