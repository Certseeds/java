// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.service;

import nanoseeds.springboot.vo.base;
import nanoseeds.springboot.vo.dao.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@AllArgsConstructor
public final class GithubService extends base {

    private final RestTemplate restTemplate;

    public String zen() {
        final String zenSentence = restTemplate.getForObject("/zen", String.class);
        return zenSentence;
    }

    public User getUserInfo() {
        return null;
    }
}
