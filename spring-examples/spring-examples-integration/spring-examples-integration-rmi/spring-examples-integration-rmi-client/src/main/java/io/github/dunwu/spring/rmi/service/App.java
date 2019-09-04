package io.github.dunwu.spring.rmi.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

@SuppressWarnings("all")
public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:/spring/spring-servlet.xml");
        System.out.println("加载Spring容器,并初始化spring-rmi-client");
        MessageProvider client = (MessageProvider) ctx.getBean("messageService");
        System.out.println("返回结果: " + client.getMessage("Zhang Peng"));
    }
}
