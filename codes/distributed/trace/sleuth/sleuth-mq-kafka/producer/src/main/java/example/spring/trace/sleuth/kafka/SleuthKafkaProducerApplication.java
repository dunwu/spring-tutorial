package example.spring.trace.sleuth.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(MySource.class)
public class SleuthKafkaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthKafkaProducerApplication.class, args);
    }

}
