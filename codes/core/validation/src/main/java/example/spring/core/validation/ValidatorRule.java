package example.spring.core.validation;

import org.springframework.validation.Errors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface ValidatorRule {

    boolean support(Annotation annotation);

    void valid(Annotation annotation, Object object, Field field, Errors errors) throws Exception;

}
