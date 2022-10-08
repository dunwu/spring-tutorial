package io.github.dunwu.springboot;

import com.alibaba.fastjson.JSON;
import io.github.dunwu.tool.io.AnsiColorUtil;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;

import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * 以三种模式批量发送 Kafka 消息
 *
 * @author Zhang Peng
 * @since 2018-11-29
 */
@SpringBootTest(classes = SpringBootMsgKafkaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KafkaProducerTest {

    private static final int BATCH_SIZE = 10000;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 绑定相同的 Topic，SimpleKafkaDemo 中有监听器负责消费 Topic
     */
    @Value("${dunwu.kafka.string-string-topic}")
    private String stringStringTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 异步发送 10000 条消息（吞吐量很高，但不可靠——不关系是否丢包）
     * <p>
     * 由于 KafkaTemplate 底层实际上使用 NIO 非阻塞传输方式，因此客户端调用代码没必要用线程去发送
     */
    @Test
    public void asyncSend() {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < BATCH_SIZE; i++) {
            KafkaTestBean<Integer> bean = new KafkaTestBean<>();
            bean.setData(i).setTimestamp(new Date());
            kafkaTemplate.send(stringStringTopic, "no-sequence", JSON.toJSONString(bean));
        }
        long end = System.currentTimeMillis();
        AnsiColorUtil.BLUE.println("耗时：" + (end - begin));
    }

    /**
     * 同步发送 10000 条消息（吞吐量很低，但可靠）
     */
    @Test
    public void syncSend() {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < BATCH_SIZE; i++) {
            KafkaTestBean<Integer> bean = new KafkaTestBean<>();
            bean.setData(i).setTimestamp(new Date());
            String value = JSON.toJSONString(bean);
            try {
                SendResult<String, String> result = kafkaTemplate.send(stringStringTopic, "no-sequence", value).get();
                log.info("Producer send result [key = {}, value = {}]", result.getProducerRecord().key(),
                    result.getProducerRecord().value());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                log.error("发送 Kafka 消息 [topic = {}, key = {}, value = {}] 失败！Exception: {}",
                    stringStringTopic, "no-sequence", value, e.getMessage());
            }
        }
        long end = System.currentTimeMillis();
        AnsiColorUtil.BLUE.println("耗时：" + (end - begin));
    }

    /**
     * 异步回调发送 10000 条消息，在回调中可以处理响应
     * <p>
     * 由于 KafkaTemplate 底层实际上使用 NIO 非阻塞传输方式，因此客户端调用代码没必要用线程去发送
     */
    @Test
    public void asyncSendWithCallback() {
        // 消息发送的监听器，用于回调返回信息
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            public void onSuccess(String topic, Integer partition, String key, String value,
                RecordMetadata recordMetadata) {
                log.info("发送数据完成。topic = {}, partition = {}, key = {}, value = {}", topic, partition, key, value);
            }

            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
                log.error("发送数据出错！topic = {}, partition = {}, key = {}, value = {}", topic, partition, key, value);
                log.error(">>>> 错误原因：{}", exception.getMessage());
            }
        });

        long begin = System.currentTimeMillis();
        for (int i = 0; i < BATCH_SIZE; i++) {
            KafkaTestBean<Integer> bean = new KafkaTestBean<>();
            bean.setData(i).setTimestamp(new Date());
            kafkaTemplate.send(stringStringTopic, "no-sequence", JSON.toJSONString(bean));
        }
        long end = System.currentTimeMillis();
        AnsiColorUtil.BLUE.println("耗时：" + (end - begin));
    }

}
