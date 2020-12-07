package sustech.dbojbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sustech.dbojbackend.exception.globalException;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HalfApiController {
    @Resource
    UserRepository userRepository;

    @Resource
    Token tokenResource;

    @GetMapping("login/reset")
    public String HalfAPIreset(@RequestParam String token, @RequestParam String newPassword, HttpServletResponse response) {
        var level = tokenResource.checkToken(token);
        if (level == null) {
            throw new globalException.ForbiddenException("reset forbidden");
        } else {
            userRepository.updatePasswordByUsername(tokenResource.gettokenUserName(token), newPassword);
            return "redirect:https://www.baidu.com/";
        }
    }
}
