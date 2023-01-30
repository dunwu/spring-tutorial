package example.spring.core.bean.annotation.life;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/META-INF/spring-life.xml");
        Auditorium auditorium = ((Auditorium) ctx.getBean("auditorium"));
        auditorium.work();
    }

}
