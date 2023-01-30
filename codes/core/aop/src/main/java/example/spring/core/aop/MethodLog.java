package example.spring.core.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * <p>
 * {@link MethodLogAspect} 是 {@link MethodLog} 注解的处理器
 * <p>
 * 被标记的方法被调用时，会将调用信息写入 DB
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLog {

    String value() default "";

}
