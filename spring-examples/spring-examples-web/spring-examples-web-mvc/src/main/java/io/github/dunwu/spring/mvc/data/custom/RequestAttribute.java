package io.github.dunwu.spring.mvc.data.custom;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestAttribute {

	String value();

}
