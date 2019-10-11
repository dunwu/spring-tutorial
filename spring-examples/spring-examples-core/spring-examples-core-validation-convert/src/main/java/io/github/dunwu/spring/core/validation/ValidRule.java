package io.github.dunwu.spring.core.validation;

import org.springframework.stereotype.Component;

@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRule {

}
