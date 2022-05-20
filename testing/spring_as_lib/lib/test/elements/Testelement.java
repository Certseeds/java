package elements;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import spring_as_lib.elements.element;

import java.util.Objects;

@SpringBootTest(value = {"service.url=https://baidu.com", "service.username=USERNAME", "service.password=PASS", "service.token=weiziama"},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
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