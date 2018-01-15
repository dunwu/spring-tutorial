package io.github.dunwu.spring.ioc.annotation.inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App2 {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-collection.xml");

        // 构造注入
        Band band = (Band) ctx.getBean("band");
        band.perform();

        // 关闭应用上下文容器，不要忘记这句话
        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
