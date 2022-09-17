package io.github.dunwu.springboot.data.elasticsearch;

import io.github.dunwu.springboot.data.constant.NamingStrategy;
import io.github.dunwu.springboot.data.constant.OrderType;
import io.github.dunwu.springboot.data.constant.QueryLogicType;
import org.springframework.data.annotation.Persistent;

import java.lang.annotation.*;

/**
 * ElasticSearch 查询注解
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
@Persistent
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface QueryDocument {

    NamingStrategy namingStrategy() default NamingStrategy.DEFAULT;

    QueryLogicType logicType() default QueryLogicType.AND;

    Order[] orders() default {};

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface Order {

        String value() default "";

        OrderType type() default OrderType.ASC;

    }

}
