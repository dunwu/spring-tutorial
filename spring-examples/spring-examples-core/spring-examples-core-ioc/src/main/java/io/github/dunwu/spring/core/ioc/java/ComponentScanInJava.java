package io.github.dunwu.spring.core.ioc.java;

import io.github.dunwu.spring.core.ioc.annotation.AnnotationComponentScan;
import io.github.dunwu.spring.core.ioc.sample.job.Teacher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 展示Java方式扫描注解
 *
 * @author Zhang Peng
 * @see AnnotationComponentScan
 */
public class ComponentScanInJava {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("io.github.dunwu.spring.ioc");
        ctx.refresh();
        Teacher teacher = (Teacher) ctx.getBean("teacher");
        System.out.println(teacher.work());
    }
}
