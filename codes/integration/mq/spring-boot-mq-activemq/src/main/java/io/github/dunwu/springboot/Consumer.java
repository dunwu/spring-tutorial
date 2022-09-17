package io.github.dunwu.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @JmsListener(destination = "io.github.dunwu.springboot")
    public void receiveQueue(String text) {
        log.info("receive message: {}", text);
    }

}
