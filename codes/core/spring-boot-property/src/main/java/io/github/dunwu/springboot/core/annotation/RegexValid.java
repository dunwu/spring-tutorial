package io.github.dunwu.springboot.core.annotation;

import io.github.dunwu.springboot.core.validator.RegexValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;

/**
 * 正则校验注解
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegexValidator.class)
public @interface RegexValid {

    String regexp();

    String message() default "不匹配正则表达式";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
