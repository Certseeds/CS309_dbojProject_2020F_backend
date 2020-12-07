package sustech.dbojbackend;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.model.request.LoginRequest;
import sustech.dbojbackend.model.request.ResetRequest;
import sustech.dbojbackend.model.request.SignInRequest;
import sustech.dbojbackend.repository.CommitLogRepository;
import sustech.dbojbackend.repository.QuestionBuildRepository;
import sustech.dbojbackend.repository.QuestionRepository;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Email;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DbojBackendApplication.class)
@AutoConfigureMockMvc
public class SessionTests {
    private static final String JsonType = "application/json";
    @Resource
    private UserRepository userRepository;
    @Resource
    private CommitLogRepository commitLogRepository;
    @Resource
    private QuestionRepository questionRepository;
    @Resource
    private QuestionBuildRepository questionBuildRepository;
    @Resource
    private Token staticToken;
    @Resource
    private Email emailResource;
    private ObjectWriter ow;

    @Autowired
    private MockMvc mvc;

    private static User firstUser;
    private static User illegalUser;

    @BeforeAll
    private static void initParams() {
        firstUser = new User(1L, "12345", "67890", "test@case.com", UserLevel.NORMAL_USER);
        illegalUser = new User(2L, "wrongName", "WrongPassword", "test@email.com", UserLevel.NORMAL_USER);
    }

    @BeforeEach
    public void beforeEachTEst() {
        ObjectMapper mapper = new ObjectMapper();
        ow = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    @Order(1)
    public void testLogin() throws Exception {
        var Login = new LoginRequest(firstUser.getUserName(), firstUser.getPassWord());
        var requestJson = ow.writeValueAsString(Login);
        var resultActions = mvc.perform(MockMvcRequestBuilders
                .post("/login")
                .contentType(JsonType)
                .content(requestJson)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var result = resultActions.getResponse().getContentAsString();
    }

    @Test
    public void testLoginFail() throws Exception {
        var Login = new LoginRequest(illegalUser.getUserName(), illegalUser.getPassWord());
        var requestJson = ow.writeValueAsString(Login);
        var resultActions = mvc.perform(MockMvcRequestBuilders
                .post("/login")
                .contentType(JsonType)
                .content(requestJson)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403))
                .andReturn();
        assertEquals("Wrong Login information", resultActions.getResponse().getContentAsString());
    }

    //@Test
    //@Order(2)
    public void testReset() throws Exception {
        emailResource.sendEmailToResetPassword("email", firstUser);
        String token = staticToken.createToken(firstUser);
        var resetRequest = new ResetRequest("shenmi@123.com", token);
        var requestJson = ow.writeValueAsString(resetRequest);
        var resultActions = mvc.perform(MockMvcRequestBuilders
                .post("/login/reset")
                .contentType(JsonType)
                .content(requestJson)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.State")
                        .value("SUCCESS"))
                .andReturn();
    }

    @Test
    @Order(3)
    public void testToken() throws Exception {
        var token = staticToken.createToken(firstUser);
        assertEquals(UserLevel.NORMAL_USER, staticToken.checkToken(token));
    }

    @Test
    @Order(4)
    public void testSignIn() throws Exception {
        var Login = new SignInRequest("abc", "123", "test@case2.com");
        String requestJson = ow.writeValueAsString(Login);
        var resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/signin")
                        .contentType(JsonType)
                        .content(requestJson)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var result = resultActions.getResponse().getContentAsString();
    }

    @Test
    @Order(5)
    public void testSignInFail() throws Exception {
        var Login = new SignInRequest(firstUser.getUserName(), firstUser.getPassWord(), firstUser.getEmail());
        String requestJson = ow.writeValueAsString(Login);
        var resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/signin")
                        .contentType(JsonType)
                        .content(requestJson)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(403))
                .andReturn();
        assertEquals("Exist Information", resultActions.getResponse().getContentAsString());
    }
}
