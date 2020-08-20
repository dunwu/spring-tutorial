package io.github.dunwu.spring.core.ioc.inject;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用xml方式来实现依赖注入
 *
 * @author Zhang Peng
 * @since 2016年7月31日
 */
public class InjectBeanDemo01 {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-inject.xml");

        // 构造注入
        Poet libai = (Poet) ctx.getBean("libai");
        libai.perform();

        // Getter/Setter注入
        Musician chopin = (Musician) ctx.getBean("chopin");
        chopin.perform();

        // 关闭应用上下文容器，不要忘记这句话
        ctx.close();
    }

}
