package example.spring.core.validation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.spring.core.validation.ValidationApplication;
import example.spring.core.validation.entity.User3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Spring 嵌入校验测试例")
@AutoConfigureMockMvc
@SpringBootTest(classes = { ValidationApplication.class })
public class ValidatorController3Tests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("测试 @NotNull")
    public void testNotNull() throws Exception {
        User3 entity = new User3();
        entity.setName("张三");
        entity.setMobile("15122223333");
        mockMvc.perform(post("/validate3/edit")
                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                   .content(objectMapper.writeValueAsString(entity)))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("测试 @NotBlank")
    public void testNotBlank() throws Exception {
        User3 entity = new User3();
        entity.setId(1L);
        mockMvc.perform(post("/validate3/edit")
                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                   .content(objectMapper.writeValueAsString(entity)))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("测试 @Size")
    public void testSize() throws Exception {
        User3 entity = new User3();
        entity.setName("张");
        mockMvc.perform(post("/validate3/edit")
                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                   .content(objectMapper.writeValueAsString(entity)))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("测试校验通过")
    public void testOk() throws Exception {
        User3.Job job = new User3.Job();
        job.setId(1L);
        job.setName("工程师");
        job.setLevel("P1");
        User3 entity = new User3();
        entity.setId(1L);
        entity.setName("张三");
        entity.setMobile("15122223333");
        entity.setJob(job);
        mockMvc.perform(post("/validate3/edit")
                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                   .content(objectMapper.writeValueAsString(entity)))
               .andExpect(status().isOk()).andReturn();
    }

}
