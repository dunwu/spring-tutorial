package example.spring.trace.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SleuthFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthFeignApplication.class, args);
    }

}
