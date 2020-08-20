package io.github.dunwu.spring.core.bean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Bean 在 IoC 容器的生命周期示例
 */
public class BeanLifeCycleDemo {

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        ClassPathXmlApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("spring/spring-beans.xml");
        // 依赖查找
        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
        // 关闭应用上下文
        applicationContext.refresh();
        applicationContext.close();
    }

}
