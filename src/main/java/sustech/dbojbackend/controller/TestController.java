package sustech.dbojbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @GetMapping("testjump")
    public String HalfAPIreset2(@RequestParam String token, @RequestParam String newPassword) {
        System.out.println("token = " + token + ", newPassword = " + newPassword);
        return "redirect:https://www.sojson.com/";
    }
    @GetMapping("testjump2")
    public String HalfAPIreset3() {
        return "token + newPassword;";
    }
}
