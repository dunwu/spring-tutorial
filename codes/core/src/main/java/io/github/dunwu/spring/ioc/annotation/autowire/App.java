package io.github.dunwu.spring.ioc.annotation.autowire;

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
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-autowire.xml");

        // 自动装配：byName
        Musician chopinbyName = (Musician) ctx.getBean("chopinbyName");
        chopinbyName.perform();

        // 自动装配：byType
        Musician chopinbyType = (Musician) ctx.getBean("chopinbyType");
        chopinbyType.perform();

        // 自动装配：constructor
        Poet libai = (Poet) ctx.getBean("libai");
        libai.perform();

        // 关闭应用上下文容器，不要忘记这句话
        ((ClassPathXmlApplicationContext) ctx).close();
    }
}
