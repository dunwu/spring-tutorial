package example.spring.core.bean.annotation;

import example.spring.core.bean.entity.fruit.Fruit;
import example.spring.core.bean.entity.fruit.Orange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

/**
 * 展示 @Resource 注解的用法
 * <p>
 *
 * @author Zhang Peng
 * @Resource 是JSR 250规定的注解。 用于bean属性的setter方法并且它指示，受影响的bean属性必须在配置时被填充在xml配置文件中， 否则容器将抛出BeanInitializationException。
 * 如果没有为@Resource指定名称，它会像@Autowired一样按照类型去寻找匹配。
 */
public class AnnotationResource {

    private static final Logger log = LoggerFactory.getLogger(AnnotationResource.class);

    @Resource(name = "apple")
    Fruit fieldA;

    Fruit fieldB;

    @Resource
    Orange fieldC;

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring-annotation.xml");
        AnnotationResource annotationResource = (AnnotationResource) ctx.getBean("annotationResource");

        log.debug("type: {}, name: {}", annotationResource.getFieldA().getClass(),
            annotationResource.getFieldA().getName());

        log.debug("type: {}, name: {}", annotationResource.getFieldB().getClass(),
            annotationResource.getFieldB().getName());

        log.debug("type: {}, name: {}", annotationResource.getFieldC().getClass(),
            annotationResource.getFieldC().getName());

        ctx.close();
    }

    public Fruit getFieldA() {
        return fieldA;
    }

    public void setFieldA(Fruit fieldA) {
        this.fieldA = fieldA;
    }

    public Fruit getFieldB() {
        return fieldB;
    }

    @Resource(name = "banana")
    public void setFieldB(Fruit fieldB) {
        this.fieldB = fieldB;
    }

    public Fruit getFieldC() {
        return fieldC;
    }

    public void setFieldC(Orange fieldC) {
        this.fieldC = fieldC;
    }

}
