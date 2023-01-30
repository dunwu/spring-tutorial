package example.spring.core.validation;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.Errors;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AbstractValidatorRule implements ValidatorRule {

    @Override
    public abstract boolean support(Annotation annotation);

    @Override
    public void valid(Annotation annotation, Object target, final Field field, final Errors errors) throws Exception {
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(target.getClass(), field.getName());
        Method reader = propertyDescriptor.getReadMethod();
        Object property = reader.invoke(target);
        validProperty(annotation, property,
            (errorCode, message) -> errors.rejectValue(field.getName(), errorCode, message));
    }

    public abstract void validProperty(Annotation annotation, Object property, PostHandler postHandler);

    interface PostHandler {

        void postHandle(String errorCode, String message);

    }

}
