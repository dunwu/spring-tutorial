package example.spring.trace.sleuth.rmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {

    String RMQ_INPUT1 = "rmq-input1";

    @Input(RMQ_INPUT1)
    SubscribableChannel rmqInput1();

}
