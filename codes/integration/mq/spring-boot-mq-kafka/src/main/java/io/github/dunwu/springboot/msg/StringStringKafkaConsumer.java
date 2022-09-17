package io.github.dunwu.springboot.msg;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 简单的 Kafka 读写消息示例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-20
 */
@Component
public class StringStringKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(id = "kafka-string-string-consumer", topics = "${dunwu.kafka.string-string-topic}")
    public void recvStringStringData(ConsumerRecord<String, String> consumerRecord) {
        log.info("收到kafka消息：{}", consumerRecord.toString());
    }

    @KafkaListener(id = "kafka-string-json-consumer", topics = "${dunwu.kafka.string-json-topic}")
    public void recvStringJsonData(ConsumerRecord<String, String> consumerRecord) {
        log.info("收到kafka消息：{}", consumerRecord.toString());
    }

}
