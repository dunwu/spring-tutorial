package io.github.dunwu.spring.ioc.java;

import io.github.dunwu.spring.ioc.annotation.AnnotationComponentScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import io.github.dunwu.spring.ioc.sample.job.Teacher;

/**
 * 展示Java方式扫描注解
 *
 * @author victor
 * @see AnnotationComponentScan
 */
public class ComponentScanInJava {
    private static final Logger log = LoggerFactory.getLogger(ComponentScanInJava.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("io.github.dunwu.spring.ioc");
        ctx.refresh();
        Teacher teacher = (Teacher) ctx.getBean("teacher");
        log.debug(teacher.work());
    }
}
