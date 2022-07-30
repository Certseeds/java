package spring_as_lib.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@Slf4j
@ConfigurationProperties(ComponentProperties.PropertiesPrefix)
public record ComponentProperties(String url, String username, String password, String token) {
    public static final String PropertiesPrefix = "service";

    @PostConstruct
    private void output() {
        log.warn(this.toString());
    }
}
