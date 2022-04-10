// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.config;

import nanoseeds.springboot.vo.base;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

public enum RestTemplateBean {
    ;

    // 大概是最佳实践? 用没有实例类的Enum做外包装,
    // 包访问权限确认只有单例package内可以访问,但是单例package内没有可访问的类
    // 确保没有类可以访问RestTemplateBeans
    @Configuration
    static class RestTemplateBeans extends base {
        private static final String basic = "https://api.github.com";

        @Bean
        static RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
            // Do any additional configuration here
            return restTemplateBuilder
                    .rootUri(basic)
                    .defaultHeader("Authorization", String.format("token %s", Account.getToken()))
                    .build();
        }
    }
}
