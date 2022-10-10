package io.github.dunwu.springboot.web.form.validator.annotation;

import io.github.dunwu.springboot.web.form.validator.MobileValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {

    String message() default "手机号不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
