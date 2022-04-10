package nanoseeds.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;


@Slf4j
@TestConfiguration
public class TestLoadConfig {

    @Test
    public void testFindALl() {
        log.info("load config");
        Assertions.assertNotEquals("114514", Account.getToken());
    }
}
