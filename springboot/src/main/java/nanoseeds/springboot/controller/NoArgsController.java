// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.controller;

import nanoseeds.springboot.service.GithubService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/noArgs")
public class NoArgsController {
    private final GithubService githubService;


    @GetMapping("/zen")
    public String zen() {
        return githubService.zen();
    }
}
