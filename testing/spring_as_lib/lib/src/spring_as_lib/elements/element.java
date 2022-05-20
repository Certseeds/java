package spring_as_lib.elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import spring_as_lib.config.ComponentProperties;

import java.util.Objects;

@Component
@EnableConfigurationProperties(ComponentProperties.class)
public class element {
    private final ComponentProperties componentProperties;

    public element(@Autowired ComponentProperties componentProperties) {
        this.componentProperties = componentProperties;
    }

    public String request() {
        return String.valueOf(Objects.hash(componentProperties.getUrl(), componentProperties.getUsername()));
    }
}