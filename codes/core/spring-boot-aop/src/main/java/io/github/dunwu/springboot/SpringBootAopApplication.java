package io.github.dunwu.springboot;

import io.github.dunwu.springboot.data.elasticsearch.service.WelcomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringBootAopApplication implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final WelcomeService welcomeService;

    public SpringBootAopApplication(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(welcomeService.getMessage());
        welcomeService.getException();
        welcomeService.print("HELLO");
    }

}
