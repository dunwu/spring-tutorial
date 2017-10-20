package io.github.dunwu.spring.ioc.annotation;

import io.github.dunwu.spring.ioc.sample.Orange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import io.github.dunwu.spring.ioc.sample.AbstractFruit;

import javax.annotation.Resource;

/**
 * 展示 @Resource 注解的用法
 * <p>@Resource 是JSR 250规定的注解。
 * 用于bean属性的setter方法并且它指示，受影响的bean属性必须在配置时被填充在xml配置文件中，
 * 否则容器将抛出BeanInitializationException。
 * 如果没有为@Resource指定名称，它会像@Autowired一样按照类型去寻找匹配。
 *
 * @author victor
 */
public class AnnotationResource {
    private static final Logger log = LoggerFactory.getLogger(AnnotationResource.class);
    @Resource(name = "apple")
    AbstractFruit fieldA;

    AbstractFruit fieldB;

    @Resource
    Orange fieldC;

    public AbstractFruit getFieldA() {
        return fieldA;
    }

    public void setFieldA(AbstractFruit fieldA) {
        this.fieldA = fieldA;
    }

    public AbstractFruit getFieldB() {
        return fieldB;
    }

    @Resource(name = "banana")
    public void setFieldB(AbstractFruit fieldB) {
        this.fieldB = fieldB;
    }

    public AbstractFruit getFieldC() {
        return fieldC;
    }

    public void setFieldC(Orange fieldC) {
        this.fieldC = fieldC;
    }

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx =
                        new ClassPathXmlApplicationContext("spring/spring-annotation.xml");
        AnnotationResource annotationResource =
                        (AnnotationResource) ctx.getBean("annotationResource");

        log.debug("type: {}, name: {}", annotationResource.getFieldA().getClass(),
                        annotationResource.getFieldA().getName());

        log.debug("type: {}, name: {}", annotationResource.getFieldB().getClass(),
                        annotationResource.getFieldB().getName());

        log.debug("type: {}, name: {}", annotationResource.getFieldC().getClass(),
                        annotationResource.getFieldC().getName());

        ctx.close();
    }
}


