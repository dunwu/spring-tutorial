package example.spring.core.bean.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 展示 @PostConstruct和@PreDestroy 注解的用法
 * <p>
 *
 * @author Zhang Peng
 * @PostConstruct和@PreDestroy是JSR 250规定的用于生命周期的注解。 从其名号就可以看出，一个是在构造之后调用的方法，一个是销毁之前调用的方法。
 */
public class AnnotationPostConstructAndPreDestroy {

    private static final Logger log = LoggerFactory.getLogger(AnnotationPostConstructAndPreDestroy.class);

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring-annotation.xml");
        AnnotationPostConstructAndPreDestroy annotationPostConstructAndPreDestroy =
            (AnnotationPostConstructAndPreDestroy) ctx
                .getBean("annotationPostConstructAndPreDestroy");
        log.debug("call main method");
        ctx.close();
    }

    @PostConstruct
    public void init() {
        log.debug("call @PostConstruct method");
    }

    @PreDestroy
    public void destroy() {
        log.debug("call @PreDestroy method");
    }

}
