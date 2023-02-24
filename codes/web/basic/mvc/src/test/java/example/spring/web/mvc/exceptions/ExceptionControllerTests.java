package example.spring.web.mvc.exceptions;

import example.spring.web.mvc.AbstractContextControllerTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class ExceptionControllerTests extends AbstractContextControllerTests {

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void controllerExceptionHandler() throws Exception {
        this.mockMvc.perform(get("/exception")).andExpect(status().isOk())
                    .andExpect(content().string("IllegalStateException handled!"));
    }

    @Test
    public void globalExceptionHandler() throws Exception {
        this.mockMvc.perform(get("/global-exception")).andExpect(status().isOk())
                    .andExpect(content().string("Handled BusinessException"));
    }

}
