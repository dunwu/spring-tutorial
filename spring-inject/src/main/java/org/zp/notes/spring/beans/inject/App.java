package org.zp.notes.spring.inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-servlet.xml");

        // 构造注入
        Poet libai = (Poet) ctx.getBean("libai");
        libai.perform();

        // Getter/Setter注入
        Musician chopin = (Musician) ctx.getBean("chopin");
        chopin.perform();

        // 关闭应用上下文容器，不要忘记这句话
        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
