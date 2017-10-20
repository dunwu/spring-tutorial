package io.github.dunwu.spring.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.springframework.validation.Errors;

public interface ValidatorRule {
    public boolean support(Annotation annotation);

    public void valid(Annotation annotation, Object object, Field field, Errors errors) throws Exception;
}
