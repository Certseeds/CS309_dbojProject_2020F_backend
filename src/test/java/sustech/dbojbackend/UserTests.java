package sustech.dbojbackend;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.Users.request.ResetRequest;
import sustech.dbojbackend.model.Users.request.SignInRequest;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.repository.UserRepository;

import javax.annotation.Resource;

import static sustech.dbojbackend.service.Token.checkToken;
import static sustech.dbojbackend.service.Token.createToken;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbojBackendApplication.class)
@AutoConfigureMockMvc
public class UserTests {
    @Autowired
    private MockMvc mvc;
    @Resource
    UserRepository userRepository;

    @Test
    public void testLogin() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/login")
                .param("username", "12345")
                .param("password", "67890"));
        resultActions.andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testToken() throws Exception {
        User u=new User("12345","67890",UserLevel.NORMAL_USER);
        u.setEmail(null);
        u.setId(1L);
        String token = createToken(u);
        System.out.println(checkToken(token,"12345",1L));
    }

    @Test
    public void testSignIn() throws Exception {
        SignInRequest signIn=new SignInRequest("abc","123");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        java.lang.String requestJson = ow.writeValueAsString(signIn);
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/signin").contentType("application/json").content(requestJson));
        resultActions.andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testReset() throws Exception {
        User u=new User("12345","67890",UserLevel.NORMAL_USER);
        u.setEmail(null);
        u.setId(1L);
        String token = createToken(u);

        ResetRequest resetRequest=new ResetRequest("12345","67890","12345",token);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        java.lang.String requestJson = ow.writeValueAsString(resetRequest);

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/login/reset").contentType("application/json").content(requestJson));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDB() throws Exception{
        userRepository.updatePasswordById(1L,"12345");
    }

}
