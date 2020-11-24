package sustech.dbojbackend;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sustech.dbojbackend.model.SqlLanguage;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.model.request.CommitDeleteRequest;
import sustech.dbojbackend.model.request.CommitQuery;
import sustech.dbojbackend.model.request.CommitUpdateQuestion;
import sustech.dbojbackend.model.request.Login;
import sustech.dbojbackend.model.request.ResetRequest;
import sustech.dbojbackend.repository.CommitLogRepository;
import sustech.dbojbackend.repository.QuestionBuildRepository;
import sustech.dbojbackend.repository.QuestionRepository;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbojBackendApplication.class)
@AutoConfigureMockMvc
public class UserTests {
    private static final String JsonType = "application/json";
    @Resource
    UserRepository userRepository;
    @Resource
    CommitLogRepository commitLogRepository;
    @Resource
    QuestionRepository questionRepository;
    @Resource
    QuestionBuildRepository questionBuildRepository;
    @Resource
    Token staticToken;
    ObjectMapper mapper;
    ObjectWriter ow;
    @Autowired
    private MockMvc mvc;

    @Before
    public void beforeEachTEst() {
        mapper = new ObjectMapper();
        ow = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    @Order(1)
    public void testLogin() throws Exception {
        var Login = new Login("12345", "67890");
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
    @Order(2)
    public void testReset() throws Exception {
        User u = new User("12345", "67890", UserLevel.NORMAL_USER);
        u.setId(1L);
        u.setEmail("test@case.com");
        String token = staticToken.createToken(u);

        var resetRequest = new ResetRequest(
                "12345", "67890", "12345", token);

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
        User u = new User("12345", "12345", UserLevel.NORMAL_USER);
        u.setEmail(null);
        u.setId(1L);
        var token = staticToken.createToken(u);
        assertTrue(staticToken.checkToken(token).equals(UserLevel.NORMAL_USER));
    }

    @Test
    public void testSignIn() throws Exception {
        var Login = new Login("abc", "123");
        String requestJson = ow.writeValueAsString(Login);
        var resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/signin")
                        .contentType(JsonType)
                        .content(requestJson)
        )
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testDB() throws Exception {
        questionBuildRepository.deleteByProgramOrder(3L);
    }

    @Test
    public void testCommitQuery() throws Exception {
        User u = new User("12345", "12345", UserLevel.NORMAL_USER);
        u.setEmail("test@case.com");
        u.setId(1L);
        String token = staticToken.createToken(u);
        CommitQuery commitQuery = new CommitQuery(2L, SqlLanguage.SQLITE, 1, "COOOOOD");
        String requestJson = ow.writeValueAsString(commitQuery);

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/commit/query").contentType(JsonType).content(requestJson));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testCommitUpdate() throws Exception {
        User u = new User("12345", "67890", UserLevel.ADMIN);
        u.setEmail(null);
        u.setId(1L);
        String token = staticToken.createToken(u);

        List<String> group = new ArrayList<>();
        group.add("sql of table 1");
        group.add("sql of table 2");
        var commitUpdateQuestion = new CommitUpdateQuestion(1L, SqlLanguage.SQLITE, group, "correct script of question1 1");

        String requestJson = ow.writeValueAsString(commitUpdateQuestion);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .post("/commit/update")
                        .header("token", token)
                        .contentType(JsonType)
                        .content(requestJson)
        )
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testCommitDelete() throws Exception {
        User u = new User("12345", "12345", UserLevel.ADMIN);
        u.setEmail(null);
        u.setId(1L);
        String token = staticToken.createToken(u);

        CommitDeleteRequest commitDeleteRequest = new CommitDeleteRequest(4L);
        String requestJson = ow.writeValueAsString(commitDeleteRequest);

        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/commit/delete")
                        .header("token", token)
                        .contentType(JsonType)
                        .content(requestJson)
        )
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testProblemsDetails() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/problems/details/3"));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testProblemsList() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/problems/list"));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

}
