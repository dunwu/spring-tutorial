package example.spring.core.validation.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.spring.core.validation.ValidationApplication;
import example.spring.core.validation.entity.User2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Spring 分组校验、自定义校验注解测试例")
@AutoConfigureMockMvc
@SpringBootTest(classes = { ValidationApplication.class })
public class ValidatorController2Tests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("@AddCheck 分组校验")
    class AddCheckTests {

        @Test
        @DisplayName("测试 @NotNull")
        public void testNotNull() throws Exception {
            User2 entity = new User2();
            entity.setName("张三");
            entity.setMobile("15122223333");
            mockMvc.perform(post("/validate2/add")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isOk());
        }

        @Test
        @DisplayName("测试 @NotBlank")
        public void testNotBlank() throws Exception {
            User2 entity = new User2();
            entity.setId(1L);
            mockMvc.perform(post("/validate2/add")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试 @Size")
        public void testSize() throws Exception {
            User2 entity = new User2();
            entity.setName("张");
            mockMvc.perform(post("/validate2/add")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试校验通过")
        public void testOk() throws Exception {
            User2 entity = new User2();
            entity.setName("张三");
            entity.setMobile("15122223333");
            mockMvc.perform(post("/validate2/add")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isOk()).andReturn();
        }

    }

    @Nested
    @DisplayName("@EditCheck 分组校验")
    class EditCheckTests {

        @Test
        @DisplayName("测试 @NotNull")
        public void testNotNull() throws Exception {
            User2 entity = new User2();
            entity.setName("张三");
            entity.setMobile("15122223333");
            mockMvc.perform(post("/validate2/edit")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试 @NotBlank")
        public void testNotBlank() throws Exception {
            User2 entity = new User2();
            entity.setId(1L);
            mockMvc.perform(post("/validate2/edit")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试 @Size")
        public void testSize() throws Exception {
            User2 entity = new User2();
            entity.setName("张");
            mockMvc.perform(post("/validate2/edit")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试自定义校验注解 @IsMobile")
        public void testIsMobile() throws Exception {
            User2 entity = new User2();
            entity.setId(1L);
            entity.setName("张三");
            entity.setMobile("123");
            mockMvc.perform(post("/validate2/edit")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("测试校验通过")
        public void testOk() throws Exception {
            User2 entity = new User2();
            entity.setId(1L);
            entity.setName("张三");
            entity.setMobile("15122223333");
            mockMvc.perform(post("/validate2/edit")
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(entity)))
                   .andExpect(status().isOk()).andReturn();
        }

    }

}
