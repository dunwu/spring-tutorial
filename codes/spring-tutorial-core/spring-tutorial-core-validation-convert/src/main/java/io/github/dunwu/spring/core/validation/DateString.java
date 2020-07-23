package io.github.dunwu.spring.core.validation;

import java.lang.annotation.*;

@Valid
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateString {

	String pattern() default "yyyy-MM-dd HH:mm:ss";

	String errorCode() default "must date";

	String message() default "must be date pattern";

}
