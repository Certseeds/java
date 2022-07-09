package spring_as_lib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(ComponentProperties.PropertiesPrefix)
public record ComponentProperties(String url, String username, String password, String token) {
    public static final String PropertiesPrefix = "service";
}
