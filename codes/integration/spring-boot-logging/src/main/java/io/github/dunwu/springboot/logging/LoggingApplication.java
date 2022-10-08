package io.github.dunwu.springboot.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

@SpringBootApplication
public class LoggingApplication {

    private static final Logger log = LoggerFactory.getLogger(LoggingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoggingApplication.class, args);
    }

    @PostConstruct
    public void logInAllLevel() {
        long begin = System.nanoTime();
        int num = 1000;
        for (int i = 0; i < num; i++) {
            log.error("Print Error Message");
            log.warn("Print Warn Message");
            log.info("Print Info Message");
            log.debug("Print Debug Message");
            log.trace("Print Trace Message");
        }
        long end = System.nanoTime();
        long time = TimeUnit.NANOSECONDS.toMillis(end - begin);
        log.info("打印 {} 条日志，耗时：{} 毫秒", num, time);
    }

}
