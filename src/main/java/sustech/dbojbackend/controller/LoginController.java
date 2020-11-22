package sustech.dbojbackend.controller;


import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.web.bind.annotation.*;
import sustech.dbojbackend.model.State;
import sustech.dbojbackend.model.request.ResetRequest;
import sustech.dbojbackend.model.response.LoginResponse;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    UserRepository userRepository;

    @GetMapping("/{username}/{password}")
    public LoginResponse Login(@PathVariable String password, @PathVariable String username) {
        User user;
        try {
            List<User> u = userRepository.findByUserNameAndPassWord(username, password);
            user = u.get(0);
        } catch (Exception e) {
            throw new RuntimeException("User not exist");
        }
        String token = new Token(userRepository).createToken(user);
        return new LoginResponse(user.getUserName(), token);

    }

    @Modifying
    @PostMapping("/reset")
    public State reset(@RequestBody ResetRequest reset) {
        User user;
        try {
            user = new Token(userRepository).checkToken(reset.getToken());
        } catch (Exception e) {
            throw new RuntimeException("Token not valid");
        }
        if (!user.getPassWord().equals(reset.getOldPassword())) {
            throw new RuntimeException("invalid old password");
        }
        Long id = user.getId();
        String newP = reset.getNewPassword();
        int n = userRepository.updatePasswordById(id, newP);
        return State.SUCCESS;
    }


}
