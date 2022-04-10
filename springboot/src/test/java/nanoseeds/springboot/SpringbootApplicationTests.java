package nanoseeds.springboot;

import nanoseeds.springboot.dto.TestUser;
import nanoseeds.springboot.service.logService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {SpringbootApplicationTests.class, logService.class, TestUser.class})
class SpringbootApplicationTests {
    @Test
    void contextLoads() {
        for (int x = 0; x < 14; x++) {
            logService.log();
        }
    }
}
