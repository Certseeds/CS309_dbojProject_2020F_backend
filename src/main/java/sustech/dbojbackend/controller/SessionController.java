package sustech.dbojbackend.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sustech.dbojbackend.exception.globalException;
import sustech.dbojbackend.model.State;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.model.request.LoginRequest;
import sustech.dbojbackend.model.request.ResetRequest;
import sustech.dbojbackend.model.request.SignInRequest;
import sustech.dbojbackend.model.response.InResponse;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Email;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;

@Slf4j
@CrossOrigin(origins = "", maxAge = 3600, allowCredentials = "true")
@RestController
public class SessionController {

    @Resource
    UserRepository userRepository;
    @Resource
    Email emailResource;
    @Resource
    Token tokenResource;
    @GetMapping("/logout")
    public String justsoso(){
        return "";
    }
    @PostMapping("/login")
    public InResponse Login(@RequestBody LoginRequest l) {
        User user;
        String token;
        var u = userRepository.findByUserNameAndPassWord(l.getUsername(), l.getPassword());
        if (u.isEmpty()) {
            throw new globalException.ForbiddenException("Wrong Login information");
            // return 403 in there
        }
        user = u.get(0);
        token = tokenResource.createToken(user);
        return new InResponse(l.getUsername(), token);
    }

    @Modifying
    @PostMapping("/login/reset")
    public State reset(@RequestBody ResetRequest reset) {
        // i dont know why i want to check token at that time
        User user;
        var Users = userRepository.findByEmail(reset.getEmail());
        if (Users.isEmpty()) {
            throw new globalException.ForbiddenException("Wrong Login information");
            // return 403 now
        }
        user = Users.get(0);
        String newP = reset.getNewPassword();
        emailResource.sendEmailToResetPassword(user, newP);
        return State.SUCCESS;
    }

    @Modifying
    @PostMapping("/signin")
    public InResponse signIn(@RequestBody SignInRequest request) {
        log.info(request.toString());
        String userName = request.getUsername();
        String passWord = request.getPassword();
        String email = request.getEmail();
        var userByName = userRepository.findByUserName(userName);
        // no same name
        var userByEmail = userRepository.findByEmail(email);
        // no same email
        boolean emailIllegal = email.indexOf("@") >= 1;
        // email must contain @
        if (!emailIllegal || !userByEmail.isEmpty() || !userByName.isEmpty()) {
            throw new globalException.ForbiddenException("Exist Information");
        }
        var user = new User(userName, passWord, email, UserLevel.CREATE);
        userRepository.save(user);
        var token = tokenResource.createToken(user);
        emailResource.sendEmailToupLevel(user, token);
        return new InResponse(userName, token);
    }

}
