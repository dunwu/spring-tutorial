package example.spring.core.bean.java;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AnnotationBeanDemo02 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationBeanDemo02.class);
        ctx.scan("io.github.dunwu.spring.core.ioc.java");
        Job job = (Job) ctx.getBean("police");
        log.debug("job: {}, work: {}", job.getClass(), job.work());
        Job job2 = (Job) ctx.getBean("police2");
        log.debug("job: {}, work: {}", job.getClass(), job2.work());
        log.debug("job == job2 ? {}", job == job2);
    }

    @Bean("police2")
    public Job getPolice() {
        return new Police();
    }

}
