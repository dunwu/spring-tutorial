package io.github.dunwu.springboot.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SpringTxFailedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTxFailedApplication.class, args);
    }

}

