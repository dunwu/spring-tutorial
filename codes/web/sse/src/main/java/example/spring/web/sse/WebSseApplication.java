package example.spring.web.sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class WebSseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSseApplication.class, args);
    }

}
