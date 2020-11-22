package sustech.dbojbackend.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    @Resource
    UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Token token=new Token(userRepository);
        System.out.println("handler");
        String accessToken = request.getHeader("token");
        if(accessToken!=null){
            try {
                return token.checkToken(accessToken)!=null;
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
