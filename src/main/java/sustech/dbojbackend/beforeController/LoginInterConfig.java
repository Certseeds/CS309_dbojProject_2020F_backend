package sustech.dbojbackend.beforeController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sustech.dbojbackend.annotatin.needToken;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterConfig implements HandlerInterceptor {
    @Resource
    Token tokenResource;

    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle getted");
        if (handler instanceof HandlerMethod) {
            needToken needtoken = ((HandlerMethod) handler).getMethodAnnotation(needToken.class);
            if (null != needtoken) {
                String token = request.getHeader("token");
                UserLevel levelNeed = needtoken.value();
                if (null != token) {
                    System.out.println("token do exist");
                    UserLevel realLevel = tokenResource.checkToken(token);
                    switch (realLevel) {
                        case ADMIN: {
                            // admin can do everything
                            return true;
                        }
                        case NORMAL_USER: {
                            // normal user can just do normal user can do
                            return realLevel == levelNeed;
                        }
                        case CREATE:
                            // just created account can not do anything
                        default: {
                            return false;
                        }
                    }
                } else {
                    System.out.println("token do not exist");
                    return false;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}