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
import sustech.dbojbackend.model.SqlLanguage;
import sustech.dbojbackend.model.UserLevel;
import sustech.dbojbackend.model.request.*;
import sustech.dbojbackend.model.data.User;
import sustech.dbojbackend.repository.CommitLogRepository;
import sustech.dbojbackend.repository.QuestionBuildRepository;
import sustech.dbojbackend.repository.QuestionRepository;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbojBackendApplication.class)
@AutoConfigureMockMvc
public class UserTests {
    @Resource
    UserRepository userRepository;

    @Resource
    CommitLogRepository commitLogRepository;

    @Resource
    QuestionRepository questionRepository;
    
    @Resource
    QuestionBuildRepository questionBuildRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testLogin() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/login/12345/67890"));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testToken() throws Exception {
        User u = new User("12345", "67890", UserLevel.NORMAL_USER);
        u.setEmail(null);
        u.setId(1L);
        String token = new Token(userRepository).createToken(u);
        System.out.println(new Token(userRepository).checkToken(token));
    }

    @Test
    public void testSignIn() throws Exception {
        SignInRequest signIn = new SignInRequest("abc", "123");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        java.lang.String requestJson = ow.writeValueAsString(signIn);
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/signin").contentType("application/json").content(requestJson));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testReset() throws Exception {
        User u = new User("12345", "67890", UserLevel.NORMAL_USER);
        u.setEmail(null);
        u.setId(1L);
        String token = new Token(userRepository).createToken(u);

        ResetRequest resetRequest = new ResetRequest("12345", "67890", "12345", token);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        java.lang.String requestJson = ow.writeValueAsString(resetRequest);

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/login/reset").contentType("application/json").content(requestJson));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDB() throws Exception {
        questionBuildRepository.deleteByProgramOrder(3L);
    }

    @Test
    public void testCommitQuery() throws Exception {
        User u = new User("12345", "12345", UserLevel.NORMAL_USER);
        u.setEmail(null);
        u.setId(1L);
        String token = new Token(userRepository).createToken(u);
        CommitQuery commitQuery = new CommitQuery(token, 2L, SqlLanguage.SQLITE, 1, "COOOOOD");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        java.lang.String requestJson = ow.writeValueAsString(commitQuery);

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/commit/query").contentType("application/json").content(requestJson));
        resultActions.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testCommitUpdate() throws Exception {
        User u = new User("12345", "67890", UserLevel.ADMIN);
        u.setEmail(null);
        u.setId(1L);
        String token = new Token(userRepository).createToken(u);

        List<String> group = new ArrayList<>();
        group.add("sql of table 1");
        group.add("sql of table 2");
        CommitUpdateQuestion commitUpdateQuestion = new CommitUpdateQuestion("testQuestion1", "description of question1", SqlLanguage.SQLITE, group, new Date(), "correct script of question1 1");
        CommitUpdateRequest commitUpdateRequest=new CommitUpdateRequest(token,commitUpdateQuestion);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        java.lang.String requestJson = ow.writeValueAsString(commitUpdateRequest);

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/commit/update").contentType("application/json").content(requestJson));
        resultActions.andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testCommitDelete() throws Exception {
        User u = new User("12345", "12345", UserLevel.ADMIN);
        u.setEmail(null);
        u.setId(1L);
        String token = new Token(userRepository).createToken(u);

        CommitDeleteRequest commitDeleteRequest = new CommitDeleteRequest(token, 4L);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        java.lang.String requestJson = ow.writeValueAsString(commitDeleteRequest);

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.delete("/commit/delete").contentType("application/json").content(requestJson));
        resultActions.andDo(MockMvcResultHandlers.print());

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
