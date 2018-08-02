package io.github.dunwu.spring.ioc.java;

import io.github.dunwu.spring.ioc.sample.job.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationBean {
    private static final Logger log = LoggerFactory.getLogger(ComponentScanInJava.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationBean.class);
        ctx.scan("io.github.dunwu.spring.ioc");
        Job job = (Job) ctx.getBean("police");
        log.debug("job: {}, work: {}", job.getClass(), job.work());
    }
}
