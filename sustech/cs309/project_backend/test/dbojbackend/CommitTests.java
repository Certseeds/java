package dbojbackend;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dbojbackend.model.SqlLanguage;
import dbojbackend.model.UserLevel;
import dbojbackend.model.data.User;
import dbojbackend.model.request.CommitDeleteRequest;
import dbojbackend.model.request.CommitQuery;
import dbojbackend.model.request.CommitUpdateQuestion;
import dbojbackend.service.Token;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DbojBackendApplication.class)
@AutoConfigureMockMvc
public class CommitTests {
    private static final String JsonType = "application/json";
    private static User firstUser;
    @Resource
    Token staticToken;
    private ObjectWriter ow;
    @Autowired
    private MockMvc mvc;

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
                            ).andDo(MockMvcResultHandlers.print())
                            .andExpect(MockMvcResultMatchers.status().isOk());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() - times);
            }));
        }
        System.out.println("start finish");
        System.out.println(System.currentTimeMillis() - times);
        for (var x : threadList) {
            sleep(2000);
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
                CommitUpdateQuestion(
                1L, SqlLanguage.MYSQL, group, "correct script of question1 1", 3L, 102400L);

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
