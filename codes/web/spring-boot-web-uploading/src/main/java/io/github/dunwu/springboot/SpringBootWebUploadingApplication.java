package io.github.dunwu.springboot;

import io.github.dunwu.springboot.storage.StorageProperties;
import io.github.dunwu.springboot.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringBootWebUploadingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebUploadingApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

}
