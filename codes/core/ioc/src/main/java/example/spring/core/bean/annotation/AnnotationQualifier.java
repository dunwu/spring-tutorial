package example.spring.core.bean.annotation;

import example.spring.core.bean.entity.fruit.Fruit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 展示 @Qualifier 的用法
 * <p>
 * 使用@Autowired时，当出现多个与类型匹配的候选集时，Spring不知道选哪个。这时可使用@Qualifier bean的名称来锁定需要的bean。
 *
 * @author Zhang Peng
 */
public class AnnotationQualifier {

    private static final Logger log = LoggerFactory.getLogger(AnnotationQualifier.class);

    @Autowired
    @Qualifier("apple") /** 去除这行，会报异常 */
        Fruit fieldA;

    Fruit fieldB;

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring-annotation.xml");

        AnnotationQualifier annotationQualifier = (AnnotationQualifier) ctx.getBean("annotationQualifier");

        log.debug("type: {}, name: {}", annotationQualifier.getFieldA().getClass(),
            annotationQualifier.getFieldA().getName());

        log.debug("type: {}, name: {}", annotationQualifier.getFieldB().getClass(),
            annotationQualifier.getFieldB().getName());
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

    @Autowired
    public void setFieldB(@Qualifier("banana") Fruit fieldB) {
        this.fieldB = fieldB;
    }

}
