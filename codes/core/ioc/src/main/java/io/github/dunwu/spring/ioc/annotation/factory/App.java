package io.github.dunwu.spring.ioc.annotation.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-beans.xml");
        BeanFactory beanFactory = ((BeanFactory) ctx.getBean("beanFactory"));
        beanFactory.work();
        ((ClassPathXmlApplicationContext) ctx).close();
    }

}
