package sustech.dbojbackend.controller;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.request.SignInRequest;
import sustech.dbojbackend.model.response.SignInResponse;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;

import java.util.List;

@RestController
public class SignInController {

    @Resource
    UserRepository userRepository;

    @Modifying
    @PostMapping("/signin")
    public SignInResponse signIn(@RequestBody SignInRequest request) {
        String userName = request.getUserName();
        String passWord = request.getPassWord();

        List<User> user = userRepository.findByUserName(userName);
        if (user.size() != 0) {
            throw new RuntimeException("User name has exist");
        }
        User u = new User(userName, passWord, UserLevel.NORMAL_USER);
        u.setEmail(null);
        userRepository.save(u);
        String token = new Token(userRepository).createToken(u);
        return new SignInResponse(u.getUserName(), token);
    }
}
