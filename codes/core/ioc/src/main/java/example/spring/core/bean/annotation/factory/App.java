package example.spring.core.bean.annotation.factory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/META-INF/spring-inject.xml");
        BeanFactory beanFactory = ((BeanFactory) ctx.getBean("beanFactory"));
        beanFactory.work();
        ctx.close();
    }

}
