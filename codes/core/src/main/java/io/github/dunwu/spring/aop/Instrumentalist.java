package io.github.dunwu.spring.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Instrumentalist implements Performer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String perform() {
        String action = "play a song";
        log.info(action);
        return action;
    }
}
