package example.spring.trace.sleuth.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SampleConsumer {

    private Logger log = LoggerFactory.getLogger(getClass());

    @StreamListener(MySink.KAFKA_INPUT1)
    public void onMessage(@Payload SampleMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
