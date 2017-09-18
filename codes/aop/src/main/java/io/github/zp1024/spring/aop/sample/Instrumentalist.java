package io.github.zp1024.spring.aop.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Instrumentalist implements Performer {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void perform() {
        log.debug("play a song");
        log.info("play a song");
        log.warn("play a song");
        log.error("play a song");
    }
}
