package io.github.dunwu.springboot.data.redis;

import io.github.dunwu.tool.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class DataRedisClusterApplication implements CommandLineRunner {

    private final UserService userService;

    public DataRedisClusterApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DataRedisClusterApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {

        Map<String, User> map = new HashMap<>();
        for (long i = 0L; i < 1000L; i++) {
            User user = new User(i, RandomUtil.randomChineseName(),
                RandomUtil.randomInt(1, 100),
                RandomUtil.randomEnum(Location.class).name(),
                RandomUtil.randomEmail());
            map.put(String.valueOf(i), user);
        }
        userService.batchSetUsers(map);

        for (int i = 0; i < 100; i++) {
            long id = RandomUtil.randomLong(0, 1000);
            User user = userService.getUser(id);
            log.info("user-{}: {}", id, user.toString());
        }

        while (true) {
            long id = RandomUtil.randomLong(0, 1000);
            User user = userService.getUser(id);
            log.info("user-{}: {}", id, user.toString());
            TimeUnit.SECONDS.sleep(5);
        }
    }

    enum Location {
        Nanjing,
        Beijing,
        Shanghai,
        Hangzhou,
        Guangzhou,
        Shenzhen
    }

}
