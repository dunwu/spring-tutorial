package io.github.dunwu.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringBootLoggingApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringBootLoggingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLoggingApplication.class, args);
    }

    @PostConstruct
    public void logInAllLevel() {
        long begin = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            log.error("Print Error Message");
            log.warn("Print Warn Message");
            log.info("Print Info Message");
            log.debug("Print Debug Message");
            log.trace("Print Trace Message");
        }
        long end = System.nanoTime();
        long time = TimeUnit.NANOSECONDS.toMillis(end - begin);
        log.info("打印 50000 条日志，耗时：{} 毫秒", time);
    }

}
