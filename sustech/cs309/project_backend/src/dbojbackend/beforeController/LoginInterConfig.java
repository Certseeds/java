package dbojbackend.beforeController;

import dbojbackend.annotatin.needToken;
import dbojbackend.model.UserLevel;
import dbojbackend.service.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterConfig implements HandlerInterceptor {
    private final Token tokenResource;

    @Autowired
    public LoginInterConfig(Token tokenResource) {
        this.tokenResource = tokenResource;
    }

    @Bean
    public LoginInterConfig loginInterConfig() {
        return this;
    }

    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle getted");
        if (!(handler instanceof HandlerMethod hm)) {
            return true;
        }
        final needToken needtoken = hm.getMethodAnnotation(needToken.class);
        if (null == needtoken) {
            return true;
        }
        final String token = request.getHeader("token");
        final UserLevel levelNeed = needtoken.value();
        if (null == token) {
            System.out.println("token do not exist");
            response.setStatus(403);
            return false;
        }
        System.out.println("token do exist");
        UserLevel realLevel = tokenResource.checkToken(token);
        if (null == realLevel) {
            response.setStatus(403);
            return false;
        }
        switch (realLevel) {
            case ADMIN -> {return true;}// admin can do everything
            case NORMAL_USER -> {
                if (realLevel == levelNeed) {
                    return true;
                } else {
                    response.setStatus(403);
                    return false;
                }
            }
            // just created account can not do anything
            default -> {
                response.setStatus(403);
                return false;
            }
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {}

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {}
}