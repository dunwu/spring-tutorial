package io.github.dunwu.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DataElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataElasticsearchApplication.class);
    }

}
