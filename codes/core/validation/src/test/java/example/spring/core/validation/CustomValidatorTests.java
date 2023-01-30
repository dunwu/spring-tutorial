package example.spring.core.validation;

import example.spring.core.validation.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;

/**
 * Spring 校验器示例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-04
 */
@DisplayName("Spring 自定义校验测试例")
@AutoConfigureMockMvc
@SpringBootTest(classes = { ValidationApplication.class })
public class CustomValidatorTests {

    @Autowired
    private CustomValidator customValidator;

    @Test
    public void testValidateSuccess() {
        Person entity = new Person();
        entity.setName("张三");
        BindException errors = new BindException(entity, "target");
        customValidator.validate(entity, errors);
        Assertions.assertEquals(0, errors.getFieldErrors().size());
    }

    @Test
    public void testValidateFailed() {
        Person entity = new Person();
        BindException errors = new BindException(entity, "target");
        customValidator.validate(entity, errors);
        Assertions.assertEquals(1, errors.getFieldErrors().size());
        errors.getFieldErrors().forEach(i -> {
            System.out.println(i.getDefaultMessage());
        });

    }

}
