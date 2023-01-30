package example.spring.core.bean.annotation;

import example.spring.core.bean.entity.fruit.Apple;
import example.spring.core.bean.entity.fruit.Banana;
import example.spring.core.bean.entity.fruit.Orange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.inject.Inject;

/**
 * 展示 @Resource 注解的用法
 *
 * @author Zhang Peng
 */
public class AnnotationInject {

    private static final Logger log = LoggerFactory.getLogger(AnnotationInject.class);

    @Inject
    Apple fieldA;

    Banana fieldB;

    Orange fieldC;

    public AnnotationInject() {
    }

    @Inject
    public AnnotationInject(Orange fieldC) {
        this.fieldC = fieldC;
    }

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring-annotation.xml");
        AnnotationInject annotationInject = (AnnotationInject) ctx.getBean("annotationInject");

        log.debug("type: {}, name: {}", annotationInject.getFieldA().getClass(),
            annotationInject.getFieldA().getName());

        log.debug("type: {}, name: {}", annotationInject.getFieldB().getClass(),
            annotationInject.getFieldB().getName());

        log.debug("type: {}, name: {}", annotationInject.getFieldC().getClass(),
            annotationInject.getFieldC().getName());

        ctx.close();
    }

    public Apple getFieldA() {
        return fieldA;
    }

    public void setFieldA(Apple fieldA) {
        this.fieldA = fieldA;
    }

    public Banana getFieldB() {
        return fieldB;
    }

    @Inject
    public void setFieldB(Banana fieldB) {
        this.fieldB = fieldB;
    }

    public Orange getFieldC() {
        return fieldC;
    }

}
