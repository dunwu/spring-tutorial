package io.github.dunwu.springboot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootProfileApplication implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;

    public SpringBootProfileApplication(MessageService messageService) {
        this.messageService = messageService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProfileApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info(this.messageService.getMessage());
    }

}
