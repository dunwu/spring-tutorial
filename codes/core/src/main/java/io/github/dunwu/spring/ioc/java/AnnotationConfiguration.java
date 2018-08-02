package io.github.dunwu.spring.ioc.java;

import io.github.dunwu.spring.ioc.sample.job.Job;
import io.github.dunwu.spring.ioc.sample.job.Police;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ComponentScanInJava.class);

    @Bean
    public Job getPolice() {
        return new Police();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationConfiguration.class);
        ctx.scan("io.github.dunwu.spring.ioc");
        Job job = (Job) ctx.getBean("police");
        log.debug("job: {}, work: {}", job.getClass(), job.work());
    }
}
