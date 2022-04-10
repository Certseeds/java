// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.config;

import nanoseeds.springboot.vo.base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

public enum Account {
    ;
    // 作为一个不能实例化,没有其他冗余方法的类用

    public static String getToken() {
        return inside.token;
    }

    @Component
    @ConfigurationProperties(prefix = "account")
    private static final class inside extends base {
        private static String token;

        @Value("${account:token}")
        void setToken(final String token) {
            inside.token = token;
        }
    }
}
