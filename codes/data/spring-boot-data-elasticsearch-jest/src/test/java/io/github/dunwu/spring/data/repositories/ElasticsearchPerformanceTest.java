package io.github.dunwu.spring.data.repositories;

import io.github.dunwu.spring.data.entities.User;
import io.github.dunwu.springboot.SpringBootDataElasticsearchApplication;
import io.github.dunwu.tool.util.RandomUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 性能测试，使用请慎重
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootDataElasticsearchApplication.class })
public class ElasticsearchPerformanceTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void before() {
        userRepository.deleteAll();
        System.out.println(userRepository.count());
    }

    @Test
    public void test() {

        // 并发写数据
        long begin = System.nanoTime();
        ExecutorService service = Executors.newFixedThreadPool(10);
        ArrayList<Callable<Object>> tasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tasks.add(() -> {
                List<User> users = new ArrayList<>();
                for (int j = 0; j < 1000; j++) {
                    User user = new User(RandomUtil.randomChineseName(), RandomUtil.randomInt(18, 99),
                        RandomUtil.randomString(6, 10), RandomUtil.randomEmail());
                    users.add(user);
                }
                return userRepository.saveAll(users);
            });
        }
        try {
            service.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
        long end = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(end - begin);
        long count = userRepository.count();

        System.out.println(String.format("the time of write %d doc: %d ms", count, millis));

        userRepository.save(new User("张三", 31, "xxxxxx", "forbreak@163.com"));

        System.out.printf("当前有 %s 条数据\n", count);

        begin = System.nanoTime();
        List<User> userList = userRepository.findByUserName("张鹏");
        assertThat(userList).isNotEmpty();
        end = System.nanoTime();
        millis = TimeUnit.NANOSECONDS.toMillis(end - begin);
        System.out.println(String.format("the time of find doc in %d data: %d ms", count, millis));
        userList.forEach(System.out::println);
    }

}
