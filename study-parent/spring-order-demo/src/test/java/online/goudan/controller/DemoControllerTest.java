package online.goudan.controller;

import online.goudan.controller.DemoController;
import online.goudan.filter.FirstFilter;
import online.goudan.service.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author lcl
 * @date 2023/8/16 10:41
 * @desc DemoControllerTest
 */
@WebMvcTest(controllers = DemoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DemoControllerTest {

    @Autowired
    private MockMvc mvc;



    @Test
    public void testHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/demo/hello")).andExpect(status().isOk()).andExpect(content().string("hello")).andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                System.out.println(result);
            }
        });
    }
}
