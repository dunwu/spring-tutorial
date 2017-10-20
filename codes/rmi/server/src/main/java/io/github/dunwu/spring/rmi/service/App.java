package io.github.dunwu.spring.rmi.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

@SuppressWarnings("all")
public class App {
    public static void main(String[] args) {
        AbstractApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:/spring/spring-servlet.xml");
        System.out.println("已成功发布spring-rmi-server");
    }
}
