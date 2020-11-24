package sustech.dbojbackend.controller;


import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sustech.dbojbackend.model.State;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.model.request.Login;
import sustech.dbojbackend.model.request.ResetRequest;
import sustech.dbojbackend.model.response.LoginResponse;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    UserRepository userRepository;

    @Resource
    Token tokenResource;

    @PostMapping()
    public LoginResponse Login(@RequestBody Login l) {
        User user = null;
        String token = "";
        try {
            var u = userRepository.findByUserNameAndPassWord(l.getUsername(), l.getPassword());
            if (!u.isEmpty()) {
                user = u.get(0);
            }
        } catch (Exception e) {
            return new LoginResponse(l.getUsername(), token);
        }
        if (user != null) {
            token = tokenResource.createToken(user);
        }
        return new LoginResponse(l.getUsername(), token);
    }

    @Modifying
    @PostMapping("/reset")
    public State reset(@RequestBody ResetRequest reset) {
        // i dont know why i want to check token at that time
        User user;
        try {
            var Users = userRepository.findByUserNameAndPassWord(reset.getUserName(), reset.getOldPassword());
            if (Users.isEmpty()) {
                return State.FAILED;
            }
            user = Users.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return State.FAILED;
        }
        Long id = user.getId();
        String newP = reset.getNewPassword();
        Integer n = userRepository.updatePasswordById(id, newP);
        return State.SUCCESS;
    }


}
