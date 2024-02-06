package example.spring.ratelimit.sentinel;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-01-30
 */
@EnableDubbo
@SpringBootApplication
public class SentinelConsumerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SentinelConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }

}
