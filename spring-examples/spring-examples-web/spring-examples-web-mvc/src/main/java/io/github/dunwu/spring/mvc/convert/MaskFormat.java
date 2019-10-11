package io.github.dunwu.spring.mvc.convert;

@Target(value = { ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MaskFormat {

	String value();

}
