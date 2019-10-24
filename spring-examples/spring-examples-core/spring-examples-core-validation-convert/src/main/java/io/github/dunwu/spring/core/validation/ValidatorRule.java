package io.github.dunwu.spring.core.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.springframework.validation.Errors;

public interface ValidatorRule {

	boolean support(Annotation annotation);

	void valid(Annotation annotation, Object object, Field field, Errors errors) throws Exception;

}
