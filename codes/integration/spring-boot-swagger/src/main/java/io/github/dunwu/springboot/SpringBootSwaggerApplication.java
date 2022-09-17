package io.github.dunwu.springboot;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动后访问：http://<host>:<port>/swagger-ui.html
 * <p>
 * Eg: http://localhost:8080/swagger-ui.html
 */
@EnableSwagger2Doc
@SpringBootApplication
public class SpringBootSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSwaggerApplication.class, args);
    }

}
