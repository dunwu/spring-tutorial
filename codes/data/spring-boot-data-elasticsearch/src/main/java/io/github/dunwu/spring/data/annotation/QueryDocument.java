package io.github.dunwu.spring.data.annotation;

import io.github.dunwu.spring.data.constant.OrderType;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface QueryDocument {

    Class<?> value();

    String orderItem() default "";

    OrderType orderType() default OrderType.ASC;

}
