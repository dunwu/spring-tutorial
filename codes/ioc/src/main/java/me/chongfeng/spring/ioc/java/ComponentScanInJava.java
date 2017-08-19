package me.chongfeng.spring.ioc.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import me.chongfeng.spring.ioc.sample.job.Teacher;

/**
 * 展示Java方式扫描注解
 *
 * @author victor
 * @see me.chongfeng.spring.ioc.annotation.AnnotationComponentScan
 */
public class ComponentScanInJava {
    private static final Logger log = LoggerFactory.getLogger(ComponentScanInJava.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("me.chongfeng.spring.beans");
        ctx.refresh();
        Teacher teacher = (Teacher) ctx.getBean("teacher");
        log.debug(teacher.work());
    }
}
