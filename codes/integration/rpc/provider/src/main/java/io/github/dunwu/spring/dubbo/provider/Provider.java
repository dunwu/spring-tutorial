package io.github.dunwu.spring.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Zhang Peng
 */
public class Provider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "META-INF/spring/dubbo-demo-provider.xml" });
        context.start();
        System.in.read(); // 按任意键退出
    }

}
