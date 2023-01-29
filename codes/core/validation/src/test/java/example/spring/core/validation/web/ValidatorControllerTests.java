package example.spring.core.validation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.spring.core.validation.ValidationApplication;
import example.spring.core.validation.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Spring 基本校验测试例")
@AutoConfigureMockMvc
@SpringBootTest(classes = { ValidationApplication.class })
public class ValidatorControllerTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("@RequestBody 参数校验")
    class RequestBodyTests {

        @Test
        @DisplayName("测试 @NotNull")
        public void testNotNull() throws Exception {
            User entity = new User();
            entity.setName("张三");
            mockMvc.perform(post("/validate1/save")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试 @NotBlank")
        public void testNotBlank() throws Exception {
            User entity = new User();
            entity.setId(1L);
            mockMvc.perform(post("/validate1/save")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试 @Size")
        public void testSize() throws Exception {
            User entity = new User();
            entity.setId(1L);
            entity.setName("张");
            mockMvc.perform(post("/validate1/save")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试 @Min")
        public void testMin() throws Exception {
            User entity = new User();
            entity.setId(1L);
            entity.setName("张三");
            entity.setAge(0);
            mockMvc.perform(post("/validate1/save")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试 @Max")
        public void testMax() throws Exception {
            User entity = new User();
            entity.setId(1L);
            entity.setName("张三");
            entity.setAge(101);
            mockMvc.perform(post("/validate1/save")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试校验通过")
        public void testOk() throws Exception {
            User entity = new User();
            entity.setId(1L);
            entity.setName("张三");
            entity.setAge(20);
            mockMvc.perform(post("/validate1/save")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isOk()).andReturn();
        }

    }

    @Nested
    @DisplayName("@RequestParam 参数校验")
    class RequestParamTests {

        @Test
        @DisplayName("测试 @NotBlank")
        public void testNotBlank() throws Exception {
            mockMvc.perform(get("/validate1/queryByName").param("username", ""))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试 @Size")
        public void testSize() throws Exception {
            mockMvc.perform(get("/validate1/queryByName").param("username", "张"))
                   .andExpect(status().isBadRequest());
        }

    }

    @Nested
    @DisplayName("@PathVariable 参数校验")
    class PathVariableTests {

        @Test
        @DisplayName("测试 @Min")
        public void testMin() throws Exception {
            mockMvc.perform(get("/validate1/detail/0"))
                   .andExpect(status().isBadRequest());
        }

    }

}
