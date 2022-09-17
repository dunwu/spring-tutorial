package io.github.dunwu.spring.mvc.views;

import io.github.dunwu.spring.mvc.AbstractContextControllerTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
public class ViewsControllerTests extends AbstractContextControllerTests {

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(status().isOk()).build();
    }

    @Test
    public void htmlView() throws Exception {
        this.mockMvc.perform(get("/views/html")).andExpect(view().name(containsString("views/html")))
                    .andExpect(model().attribute("foo", "bar")).andExpect(model().attribute("fruit", "apple"))
                    .andExpect(model().size(2));
    }

    @Test
    public void viewName() throws Exception {
        this.mockMvc.perform(get("/views/viewName")).andExpect(view().name(containsString("views/viewName")))
                    .andExpect(model().attribute("foo", "bar")).andExpect(model().attribute("fruit", "apple"))
                    .andExpect(model().size(2));
    }

    @Test
    public void uriTemplate() throws Exception {
        this.mockMvc.perform(get("/views/pathVariables/bar/apple"))
                    .andExpect(view().name(containsString("views/html")));
    }

}
