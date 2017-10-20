package io.github.dunwu.spring.ioc.annotation.inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @title App
 * @description 使用xml方式来实现依赖注入
 * @author victor
 * @date 2016年7月31日
 */
public class App {
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-beans.xml");

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
