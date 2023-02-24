package example.spring.web.mvc.data;

import example.spring.web.mvc.data.custom.CustomArgumentController;
import example.spring.web.mvc.data.custom.CustomArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CustomArgumentControllerTests {

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(new CustomArgumentController())
            .setCustomArgumentResolvers(new CustomArgumentResolver()).build();
    }

    @Test
    public void param() throws Exception {
        this.mockMvc.perform(get("/data/custom"))
                    .andExpect(content().string("Got 'foo' request attribute value 'bar'"));
    }

}
