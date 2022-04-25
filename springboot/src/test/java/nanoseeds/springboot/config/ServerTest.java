package nanoseeds.springboot.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ServerTest {
    @Autowired
    private Server server;

    @Test
    public void test() {
        Assertions.assertNotNull(server);
        Assertions.assertNotNull(server.getUrl());
    }
}