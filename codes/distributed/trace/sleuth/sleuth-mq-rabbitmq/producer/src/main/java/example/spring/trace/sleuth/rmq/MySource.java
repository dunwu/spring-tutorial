package example.spring.trace.sleuth.rmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySource {

    @Output("rmq-output1")
    MessageChannel rmqOutput1();

}
