package example.spring.trace.sleuth.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(MySink.class)
public class SleuthKafkaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthKafkaConsumerApplication.class, args);
    }

}
