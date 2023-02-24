package example.spring.web;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A Basic Spring MVC Test for the Sample Controller"
 *
 * @author Biju Kunjummen
 * @author Doo-Hwan, Kwak
 */
@SpringBootTest
public class MessageControllerWebTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCreate() throws Exception {
        this.mockMvc.perform(post("/").param("text", "FOO text").param("summary", "FOO")).andExpect(status().isFound())
                    .andExpect(header().string("location", RegexMatcher.matches("/[0-9]+")));
    }

    @Test
    public void testCreateValidation() throws Exception {
        this.mockMvc.perform(post("/").param("text", "").param("summary", "")).andExpect(status().isOk())
                    .andExpect(content().string(containsString("is required")));
    }

    @Test
    public void testHome() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk())
                    .andExpect(content().string(containsString("<title>Messages")));
    }

    private static class RegexMatcher extends TypeSafeMatcher<String> {

        private final String regex;

        RegexMatcher(String regex) {
            this.regex = regex;
        }

        public static org.hamcrest.Matcher<java.lang.String> matches(String regex) {
            return new RegexMatcher(regex);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a string that matches regex: ").appendText(this.regex);
        }

        @Override
        public boolean matchesSafely(String item) {
            return Pattern.compile(this.regex).matcher(item).find();
        }

        @Override
        public void describeMismatchSafely(String item, Description mismatchDescription) {
            mismatchDescription.appendText("was \"").appendText(item).appendText("\"");
        }

    }

}
