package example.spring.trace.sleuth.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {

    String KAFKA_INPUT1 = "kafka-input1";

    @Input(KAFKA_INPUT1)
    SubscribableChannel kafkaInput1();

}
