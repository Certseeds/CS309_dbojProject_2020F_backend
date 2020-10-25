package sustech.dbojbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.repository.UserRepository;

import javax.annotation.Resource;

/**
 * @author nanos
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserRepository userRepository;

    @GetMapping("/query/{username}/{password}")
    public User existUser(@PathVariable("username") String UserName,
                          @PathVariable("password") String Password) {
        System.out.println(UserName + " " + Password);
        User returned;
        try {
            returned = userRepository.findByUserNameAndPassWord(UserName, Password).get(0);
        } catch (Exception e) {
            returned = new User();
        }
        return returned;
    }

}