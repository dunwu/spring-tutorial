package example.spring.ratelimit.sentinel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-01-30
 */
@SpringBootApplication
public class SpringBootSentinelApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSentinelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
