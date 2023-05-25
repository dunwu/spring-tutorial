package example.spring.trace.sleuth.rmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(MySource.class)
public class SleuthRabbitmqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthRabbitmqProducerApplication.class, args);
    }

}
