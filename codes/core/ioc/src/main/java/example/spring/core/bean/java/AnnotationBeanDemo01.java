package example.spring.core.bean.java;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AnnotationBeanDemo01 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationBeanDemo01.class);
        ctx.scan("io.github.dunwu.spring.core.ioc.java");
        Job job = (Job) ctx.getBean("police");
        log.debug("job: {}, work: {}", job.getClass(), job.work());

        Teacher teacher = (Teacher) ctx.getBean("teacher");
        log.debug("job: {}, work: {}", teacher.getClass(), teacher.work());
    }

}
