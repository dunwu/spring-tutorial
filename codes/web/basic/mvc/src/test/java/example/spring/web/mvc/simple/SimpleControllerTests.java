package example.spring.web.mvc.simple;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SimpleControllerTests {

    Logger logger = LoggerFactory.getLogger(SimpleControllerTests.class);

    @Test
    public void simple() throws Exception {
        logger.error("simple");
        standaloneSetup(new SimpleController()).build().perform(get("/simple")).andExpect(status().isOk())
                                               .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                                               .andExpect(content().string("Hello world!"));
    }

}
