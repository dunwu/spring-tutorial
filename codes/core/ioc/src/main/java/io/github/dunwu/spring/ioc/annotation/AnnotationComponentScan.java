package io.github.dunwu.spring.ioc.annotation;

import io.github.dunwu.spring.ioc.java.ComponentScanInJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import io.github.dunwu.spring.ioc.java.AnnotationConfiguration;
import io.github.dunwu.spring.ioc.sample.job.Teacher;

/**
 * 展示使用注解方式标记扫描的类。
 *
 * @author victor
 * @see ComponentScanInJava
 */
@Configuration
@ComponentScan(basePackages = "io.github.dunwu.spring.ioc")
public class AnnotationComponentScan {
    private static final Logger log = LoggerFactory.getLogger(AnnotationComponentScan.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationConfiguration.class);
        Teacher teacher = (Teacher) ctx.getBean("teacher");
        log.debug(teacher.work());
    }
}
