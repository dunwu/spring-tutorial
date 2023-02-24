package example.spring.web.connections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("Spring MVC 测试")
@SpringBootTest
@AutoConfigureMockMvc
public class WebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("访问首页")
    public void homePage() throws Exception {
        // N.B. jsoup can be useful for asserting HTML content
        mockMvc.perform(get("/index.html"))
               .andExpect(content().string(containsString("Get your greeting")));
    }

    @Test
    @DisplayName("访问 /greeting")
    public void greeting() throws Exception {
        mockMvc.perform(get("/greeting"))
               .andExpect(content().string(containsString("Hello, World!")));
    }

    @Test
    @DisplayName("访问 /greeting?name=Greg")
    public void greetingWithUser() throws Exception {
        mockMvc.perform(get("/greeting").param("name", "Greg"))
               .andExpect(content().string(containsString("Hello, Greg!")));
    }

}
