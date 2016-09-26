package org.zp.notes.spring.ioc.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zp.notes.spring.ioc.sample.job.Job;
import org.zp.notes.spring.ioc.sample.job.Police;

@Configuration
public class AnnotationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ComponentScanInJava.class);

    @Bean
    public Job getPolice() {
        return new Police();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationConfiguration.class);
        ctx.scan("org.zp.notes.spring.beans");
        ctx.refresh();
        Job job = (Job) ctx.getBean("police");
        log.debug("job: {}, work: {}", job.getClass(), job.work());
    }
}
