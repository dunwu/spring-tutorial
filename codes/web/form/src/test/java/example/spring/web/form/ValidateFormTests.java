package example.spring.web.form;

import example.spring.web.form.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * 表单校验测试集
 * <p>
 * 模拟访问 {@link WebController#checkPersonInfo}，触发校验表单对象 {@link User}
 *
 * @see User
 */
@DisplayName("表单校验测试集")
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "logging.level.org.springframework.web=DEBUG")
public class ValidateFormTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("测试校验通过")
    public void testOk() throws Exception {
        MockHttpServletRequestBuilder okExample = post("/")
            .param("name", "Jack")
            .param("age", "18")
            .param("nickname", "杰克")
            .param("email", "xxx@xxx.com")
            .param("mobile", "15112345678");
        mockMvc.perform(okExample).andExpect(model().hasNoErrors());
    }

    @Test
    @DisplayName("测试 @NotNull 校验注解")
    public void testNotNull() throws Exception {
        MockHttpServletRequestBuilder example = post("/").param("name", "Jack");
        String result = mockMvc.perform(example)
                               .andExpect(model().hasErrors())
                               .andReturn()
                               .getResponse()
                               .getContentAsString();
        Assertions.assertThat(result).contains("must not be null");
        System.err.println(result);
    }

    @Test
    @DisplayName("测试 @NotBlank 校验注解")
    public void testNotBlank() throws Exception {
        MockHttpServletRequestBuilder example = post("/").param("age", "18");
        String result = mockMvc.perform(example)
                               .andExpect(model().hasErrors())
                               .andReturn()
                               .getResponse()
                               .getContentAsString();
        Assertions.assertThat(result).contains("must not be blank");
        System.err.println(result);
    }

    @Test
    @DisplayName("测试 @Size 校验注解")
    public void testSize() throws Exception {
        MockHttpServletRequestBuilder example = post("/")
            .param("name", "A")
            .param("age", "18");
        String result = mockMvc.perform(example)
                               .andExpect(model().hasErrors())
                               .andReturn()
                               .getResponse()
                               .getContentAsString();
        Assertions.assertThat(result).contains("size must be between 2 and 30");
        System.err.println(result);
    }

    @Test
    @DisplayName("测试 @Min 和 @Max 校验注解")
    public void testMinAndMax() throws Exception {
        MockHttpServletRequestBuilder minExample = post("/")
            .param("name", "Jack")
            .param("age", "1");
        String result = mockMvc.perform(minExample)
                               .andExpect(model().hasErrors())
                               .andReturn()
                               .getResponse()
                               .getContentAsString();
        Assertions.assertThat(result).contains("must be greater than or equal to 18");
        System.err.println(result);

        MockHttpServletRequestBuilder maxExample = post("/").param("name", "Jack").param("age", "101");
        String result2 = mockMvc.perform(maxExample)
                                .andExpect(model().hasErrors())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();
        Assertions.assertThat(result2).contains("must be less than or equal to 100");
        System.err.println(result2);
    }

    @Test
    @DisplayName("测试 @Email 校验注解")
    public void testEmail() throws Exception {
        MockHttpServletRequestBuilder example = post("/")
            .param("name", "Jack")
            .param("age", "18")
            .param("email", "xxx@");
        String result = mockMvc.perform(example)
                               .andExpect(model().hasErrors())
                               .andReturn()
                               .getResponse()
                               .getContentAsString();
        Assertions.assertThat(result).contains("must be a well-formed email address");
        System.err.println(result);
    }

    @Test
    @DisplayName("测试 @Positive 校验注解")
    public void testPositive() throws Exception {
        MockHttpServletRequestBuilder example = post("/")
            .param("name", "Jack")
            .param("age", "18")
            .param("income", "-1");
        String result = mockMvc.perform(example)
                               .andExpect(model().hasErrors())
                               .andReturn()
                               .getResponse()
                               .getContentAsString();
        Assertions.assertThat(result).contains("must be greater than 0");
        System.err.println(result);
    }

    @Test
    @DisplayName("测试 @Pattern 校验注解")
    public void testPattern() throws Exception {
        MockHttpServletRequestBuilder example = post("/")
            .param("name", "Jack")
            .param("age", "18")
            .param("nickname", "Jack");
        String result = mockMvc.perform(example)
                               .andExpect(model().hasErrors())
                               .andReturn()
                               .getResponse()
                               .getContentAsString();
        Assertions.assertThat(result).contains("must match");
        System.err.println(result);
    }

    @Test
    @DisplayName("测试自定义的 @Mobile 校验注解")
    public void testMobile() throws Exception {
        MockHttpServletRequestBuilder example = post("/")
            .param("name", "Jack")
            .param("age", "18")
            .param("mobile", "11122223333");
        String result = mockMvc.perform(example)
                               .andExpect(model().hasErrors())
                               .andReturn()
                               .getResponse()
                               .getContentAsString();
        Assertions.assertThat(result).contains("手机号不正确");
        System.err.println(result);
    }

}
