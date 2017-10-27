package io.github.dunwu.spring.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.github.dunwu.spring.dubbo.api.HelloProvider;

/**
 * @author Zhang Peng
 */
public class Consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "META-INF/spring/dubbo-demo-consumer.xml" });
        context.start();

        // 获取远程服务代理
        HelloProvider demoService = (HelloProvider) context.getBean("demoService");
        // 执行远程方法
        String hello = demoService.sayHello("world");
        // 显示调用结果
        System.out.println(hello);
    }
}
