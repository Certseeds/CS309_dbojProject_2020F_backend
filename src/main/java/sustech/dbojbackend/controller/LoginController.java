package sustech.dbojbackend.controller;


import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sustech.dbojbackend.model.Users.request.ResetRequest;
import sustech.dbojbackend.model.Users.response.LoginResponse;
import sustech.dbojbackend.model.Users.response.ResetResponse;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.repository.UserRepository;

import javax.annotation.Resource;

import java.util.List;

import static sustech.dbojbackend.service.Token.checkToken;
import static sustech.dbojbackend.service.Token.createToken;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    UserRepository userRepository;

    @GetMapping("")
    public LoginResponse Login(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password) {
        User user;
        try {
            List<User> u = userRepository.findByUserNameAndPassWord(username, password);
            user = u.get(0);
        } catch (Exception e) {
            throw new RuntimeException("User not exist");
        }
        String token = createToken(user);
        return new LoginResponse(user.getUserName(), token);

    }

    @Modifying
    @PostMapping("/reset")
    public ResetResponse reset(@RequestBody ResetRequest reset) {
        User user;
        try {
            List<User> u = userRepository.findByUserNameAndPassWord(reset.getUserName(), reset.getOldPassword());
            user = u.get(0);
        } catch (Exception e) {
            throw new RuntimeException("User not exist");
        }
        if (!checkToken(reset.getToken(), user.getUserName(), user.getId())) {
            throw new RuntimeException("Token not valid");
        }
        Long id = user.getId();
        String newP = reset.getNewPassword();
        int n = userRepository.updatePasswordById(id, newP);
        return new ResetResponse(true);
    }


}
