package nanoseeds.springboot.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class NoArgsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void zen() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        //这个里get，post，put，delete分别对用自己接口设定里面是url，{id}是PathVariable注解传递参数，后面是值
                        MockMvcRequestBuilders.get("/v1/noArgs/zen")
                                //设定一些基本，json传递
                                .contentType(MediaType.APPLICATION_JSON))
                // 期待返回的服务器状态码 200，500，404等等
                .andExpect(status().isOk())
                .andReturn();
        //输出返回内容
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());
        System.out.printf("zen is %s%n", mvcResult.getResponse().getContentAsString());
    }
}