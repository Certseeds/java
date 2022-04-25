// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties("spring.datasource")
public class Server {

    public Server(String url) {
        this.url = url;
    }

    private final String url;
}
