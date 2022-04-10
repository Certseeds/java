package nanoseeds.springboot.service;

import nanoseeds.springboot.vo.base;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public final class TestGithubService extends base {
    private final GithubService githubService;

    @Autowired
    public TestGithubService(GithubService githubService) {
        this.githubService = githubService;
    }

    @Test
    public void ensure() {
        githubService.zen();
    }
}
