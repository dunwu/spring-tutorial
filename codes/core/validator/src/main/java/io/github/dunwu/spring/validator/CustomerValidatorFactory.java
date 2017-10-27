package io.github.dunwu.spring.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class CustomerValidatorFactory implements Validator {
    @Autowired
    private CustomerValidatorConfig customerValidatorConfig;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        List<Field> fileds = getFields(target.getClass());
        for (Field field : fileds) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getAnnotation(Valid.class) != null) {
                    try {
                        ValidatorRule customerValidatorRule = customerValidatorConfig.findRule(annotation);
                        if (customerValidatorRule != null) {
                            customerValidatorRule.valid(annotation, target, field, errors);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private List<Field> getFields(Class<? extends Object> clazz) {
        // 声明Field数组
        List<Field> fields = new ArrayList<Field>();
        // 如果class类型不为空
        while (clazz != null) {
            // 添加属性到属性数组
            Collections.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
