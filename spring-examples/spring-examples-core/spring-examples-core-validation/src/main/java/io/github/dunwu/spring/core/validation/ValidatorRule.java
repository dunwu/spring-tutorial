package io.github.dunwu.spring.core.validation;

import org.springframework.validation.Errors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface ValidatorRule {
    public boolean support(Annotation annotation);

    public void valid(Annotation annotation, Object object, Field field, Errors errors) throws Exception;
}
