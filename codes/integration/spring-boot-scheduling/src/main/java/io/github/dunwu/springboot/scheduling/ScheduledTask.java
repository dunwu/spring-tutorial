package io.github.dunwu.springboot.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Spring Boot Schedule 示例
 *
 * @author Zhang Peng
 * @see <a href="http://spring.io/guides/gs/scheduling-tasks/">scheduling-tasks</a>
 * @see <a href= "https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling">Task
 * Execution and Scheduling</a>
 */
@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    @Scheduled(cron = "0/10 * * * * ? ")
    public void cron() {
        log.info("[cron]The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedDelay = 5000)
    public void fixedDelay() {
        log.info("[fixedDelay]The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 5000)
    public void fixedRate() {
        log.info("[fixedRate]The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void initialDelay() {
        log.info("[initialDelay]The time is now {}", dateFormat.format(new Date()));
    }

}
