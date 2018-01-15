package io.github.dunwu.spring.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/spring-servlet.xml")
public class ValidatorDemoTest {
    @Autowired
    private Validator customerValidatorFactory;

    @Test
    public void testValidateSuccess() {
        Form form = new Form();
        form.setCurrent("2015-11-11 18:00:00");
        BindException errors = new BindException(form, "target");
        customerValidatorFactory.validate(form, errors);
        Assert.assertEquals(0, errors.getFieldErrors().size());
    }

    @Test
    public void testValidateFailed() {
        Form form = new Form();
        form.setCurrent("2015-11-11 18-00-00");
        BindException errors = new BindException(form, "target");
        customerValidatorFactory.validate(form, errors);
        Assert.assertNotEquals(0, errors.getFieldErrors().size());
    }
}
