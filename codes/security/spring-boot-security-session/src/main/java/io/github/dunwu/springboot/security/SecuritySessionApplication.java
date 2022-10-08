package io.github.dunwu.springboot.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "io.github.dunwu" })
@MapperScan("io.github.dunwu.springboot.security.mapper")
public class SecuritySessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuritySessionApplication.class);
    }

}
