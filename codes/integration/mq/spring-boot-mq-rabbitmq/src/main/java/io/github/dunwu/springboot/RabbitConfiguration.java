package io.github.dunwu.springboot;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    private final static String QUEUE_NAME = "test";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

}
