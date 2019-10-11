package io.github.dunwu.spring.core.validation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindException;

/**
 * Spring 校验器示例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-validation.xml")
public class SpringValidatorTest {

	@Autowired
	private PersonValidator personValidator;

	@Test
	public void testValidateSuccess() {
		Person person = new Person();
		person.setBirthday("2015-11-11 18:00:00");
		BindException errors = new BindException(person, "target");
		personValidator.validate(person, errors);
		Assert.assertEquals(0, errors.getFieldErrors().size());
	}

	@Test
	public void testValidateFailed() {
		Person person = new Person();
		BindException errors = new BindException(person, "target");
		personValidator.validate(person, errors);
		Assert.assertNotEquals(0, errors.getFieldErrors().size());
	}

	@Test
	public void testValidateFailed2() {
		Person form = new Person();
		form.setBirthday("2015-11-11 18-00-00");
		BindException errors = new BindException(form, "target");
		personValidator.validate(form, errors);
		Assert.assertNotEquals(0, errors.getFieldErrors().size());
	}

}
