package io.github.dunwu.spring.ioc.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-annotation.xml");

        // 自动装配：byName
        Musician musician = (Musician) ctx.getBean("musician");
        musician.setName("艺术家");
        musician.setSong("夜曲");
        musician.perform();

        // 关闭应用上下文容器，不要忘记这句话
        ((ClassPathXmlApplicationContext) ctx).close();
    }

}
