package example.spring.trace.sleuth.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySource {

    @Output("kafka-output1")
    MessageChannel kafkaOutput1();

}
