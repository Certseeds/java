package spring_as_lib.elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ComponentScan("spring_as_lib")
class TestElement {
    @Autowired
    private element E;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(E.request());
        Assertions.assertEquals(
                E.request(),
                String.valueOf(Objects.hash("https://baidu.com", "USERNAME"))
        );
    }

    @SpringBootApplication
    static class TestConfiguration {}
}