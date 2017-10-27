package io.github.dunwu.spring.ioc.annotation.life;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-life.xml");
        Auditorium auditorium = ((Auditorium) ctx.getBean("auditorium"));
        auditorium.work();
    }

}
