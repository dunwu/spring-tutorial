package io.github.dunwu.spring.ioc.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 展示 @Required 注解的用法
 * <p>@Required 注解用于bean属性的setter方法并且它指示，受影响的bean属性必须在配置时被填充在xml配置文件中，
 * 否则容器将抛出BeanInitializationException。
 *
 * @author victor
 */
public class AnnotationRequired {
    private static final Logger log = LoggerFactory.getLogger(AnnotationRequired.class);

    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    @Required
    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx =
                        new ClassPathXmlApplicationContext("spring/spring-annotation.xml");

        AnnotationRequired annotationRequired =
                        (AnnotationRequired) ctx.getBean("annotationRequired");
        log.debug("name: {}", annotationRequired.getName());
        ctx.close();
    }
}
