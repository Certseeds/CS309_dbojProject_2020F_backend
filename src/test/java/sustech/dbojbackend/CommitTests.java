package sustech.dbojbackend;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import sustech.dbojbackend.repository.CommitLogRepository;
import sustech.dbojbackend.repository.QuestionBuildRepository;
import sustech.dbojbackend.repository.QuestionRepository;
import sustech.dbojbackend.repository.UserRepository;
import sustech.dbojbackend.service.Token;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DbojBackendApplication.class)
@AutoConfigureMockMvc
public class CommitTests {
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

    private ObjectWriter ow;

    @Autowired
    private MockMvc mvc;

    private static User firstUser;

    @BeforeAll
    private static void initParams() {
        firstUser = new User(1L, "12345", "67890", "test@case.com", UserLevel.NORMAL_USER);
    }

    @BeforeEach
    public void beforeEachTEst() {
        ObjectMapper mapper = new ObjectMapper();
        ow = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    public void testDB() throws Exception {
        //questionBuildRepository.deleteByProgramOrder(3L);
    }
    @Test
    public void testCommitQuery() throws Exception {
        String token = staticToken.createToken(firstUser);
        var commitQuery =
                new CommitQuery(2L, SqlLanguage.MYSQL,
                        0, "select * from usertable;",
                        "12345");
        Long times = System.currentTimeMillis();
        String requestJson = ow.writeValueAsString(commitQuery);
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(() -> {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String token1 = staticToken.createToken(firstUser);
                var commitQuery1 =
                        new CommitQuery(2L, SqlLanguage.MYSQL,
                                0, "select * from usertable;",
                                "12345");
                String requestJson1 = null;
                try {
                    requestJson1 = ow.writeValueAsString(commitQuery1);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                try {
                    ResultActions resultActions = mvc.perform(
                            MockMvcRequestBuilders
                                    .post("/commit/query")
                                    .header("token", token1)
                                    .contentType(JsonType)
                                    .content(requestJson1)
                    )
                            .andExpect(MockMvcResultMatchers.status().isOk());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() - times);
            }));
        }
        System.out.println("start finish");
        System.out.println(System.currentTimeMillis() - times);
        for (var x: threadList){
            x.start();
        }
        for (var x : threadList) {
            x.join();
        }
        System.out.println(System.currentTimeMillis() - times);
    }

    @Test
    public void testCommitUpdate() throws Exception {
        String token = staticToken.createToken(firstUser);
        List<String> group = new ArrayList<>();
        group.add("sql of table 1");
        group.add("sql of table 2");
        var commitUpdateQuestion = new
                CommitUpdateQuestion(1L, SqlLanguage.MYSQL, group, "correct script of question1 1");

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
        String token = staticToken.createToken(firstUser);

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
