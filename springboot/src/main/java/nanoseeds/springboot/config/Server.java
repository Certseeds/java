// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * read config bind to Immutable Config class
 */
@Getter
@ConstructorBinding
@ConfigurationProperties("spring.datasource")
public class Server {

    private final String url;

    public Server(String url) {
        this.url = url;
    }
}
