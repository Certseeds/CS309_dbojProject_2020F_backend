package sustech.dbojbackend.controller;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.model.request.Login;
import sustech.dbojbackend.model.response.LoginResponse;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;

@RestController
public class SignInController {

    @Resource
    UserRepository userRepository;

    @Resource
    Token tokenResource;

    @Modifying
    @PostMapping("/signin")
    public LoginResponse signIn(@RequestBody Login request) {
        var userName = request.getUsername();
        var passWord = request.getPassword();

        var user = userRepository.findByUserName(userName);
        if (!user.isEmpty()) {
            return new LoginResponse(request.getUsername(), "");
        }
        var u = new User(userName, passWord, UserLevel.NORMAL_USER);
        u.setEmail(null);
        userRepository.save(u);
        var token = tokenResource.createToken(u);
        return new LoginResponse(request.getUsername(), token);
    }
}
