package example.spring.core.validation;

import example.spring.core.validation.annotation.Valid;
import example.spring.core.validation.config.CustomValidatorConfig;
import example.spring.core.validation.entity.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 实现 {@link Validator} 的校验器
 */
@Component
public class CustomValidator implements Validator {

    private final CustomValidatorConfig validatorConfig;

    public CustomValidator(CustomValidatorConfig validatorConfig) {
        this.validatorConfig = validatorConfig;
    }

    /**
     * 本校验器只针对 Person 校验
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");

        List<Field> fields = getFields(target.getClass());
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getAnnotation(Valid.class) != null) {
                    try {
                        ValidatorRule validatorRule = validatorConfig.findRule(annotation);
                        if (validatorRule != null) {
                            validatorRule.valid(annotation, target, field, errors);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private List<Field> getFields(Class<?> clazz) {
        // 声明Field数组
        List<Field> fields = new ArrayList<>();
        // 如果class类型不为空
        while (clazz != null) {
            // 添加属性到属性数组
            Collections.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

}
