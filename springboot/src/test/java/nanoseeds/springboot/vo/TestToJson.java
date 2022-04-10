package nanoseeds.springboot.vo;

import nanoseeds.springboot.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@JsonTest
public class TestToJson {
    private static final jsonObj obj = jsonObj.builder()
            .str1("hello")
            .str2("thank you")
            .d1(114.514d)
            .l1(114514L)
            .d2(1919.810d)
            .l2(1919810)
            .build();

    @Test
    public void test1() {
        log.info(obj.toString());
        log.info(JsonUtil.to(obj));
        log.info(JsonUtil.to(jsonObj.of()));
        log.info(JsonUtil.to(jsonObj.of()));
    }

    @Test
    public void test2() throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(ResourceUtils.getFile("classpath:vo/TestToJson/str.json").toURI()))) {
            final String jsonStr = lines.collect(Collectors.joining());
            final jsonObj json = JsonUtil.from(jsonStr, jsonObj.class);
            Assertions.assertEquals(json, obj);
        }
    }

}
