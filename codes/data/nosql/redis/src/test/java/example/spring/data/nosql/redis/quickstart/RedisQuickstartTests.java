package example.spring.data.nosql.redis.quickstart;

import io.github.dunwu.tool.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis 基础应用测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-31
 */
@Slf4j
@DisplayName("Redis 单点服务访问测试")
@ActiveProfiles(value = { "single" })
@SpringBootTest(classes = { RedisQuickstartApplication.class })
public class RedisQuickstartTests {

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        final long SIZE = 1000L;
        Map<String, User> map = new HashMap<>();
        for (long i = 0; i < SIZE; i++) {
            User user = new User(i, RandomUtil.randomChineseName(),
                RandomUtil.randomInt(1, 100),
                RandomUtil.randomEnum(Location.class).name(),
                RandomUtil.randomEmail());
            map.put(String.valueOf(i), user);
        }
        userService.batchSetUsers(map);
        long count = userService.count();
        Assertions.assertThat(count).isEqualTo(SIZE);

        for (int i = 0; i < 100; i++) {
            long id = RandomUtil.randomLong(0, 1000);
            User user = userService.getUser(id);
            log.info("user-{}: {}", id, user.toString());
        }
    }

}
