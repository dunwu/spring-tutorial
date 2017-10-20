package io.github.dunwu.spring.validator;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.Errors;

public abstract class AbastractValidatorRule implements ValidatorRule {
    public abstract boolean support(Annotation annotation);

    public void valid(Annotation annotation, Object target, final Field field, final Errors errors) throws Exception {
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(target.getClass(), field.getName());
        Method reader = propertyDescriptor.getReadMethod();
        Object property = reader.invoke(target);
        validProperty(annotation, property, new PostHandler() {
            public void postHanle(String errorCode, String message) {
                errors.rejectValue(field.getName(), errorCode, message);
            }
        });
    }

    static interface PostHandler {
        public void postHanle(String errorCode, String message);
    }

    public abstract void validProperty(Annotation annotation, Object property, PostHandler postHandler);
}
