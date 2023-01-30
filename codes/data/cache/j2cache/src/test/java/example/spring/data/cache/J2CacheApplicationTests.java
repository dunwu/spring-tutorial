package example.spring.data.cache;

import example.spring.data.cache.config.J2CacheAutoConfiguration;
import example.spring.data.cache.config.J2CacheSpringCacheAutoConfiguration;
import example.spring.data.cache.config.J2CacheSpringRedisAutoConfiguration;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Random;

@SpringBootTest(
    classes = { TestService.class, J2CacheAutoConfiguration.class, J2CacheSpringCacheAutoConfiguration.class,
        J2CacheSpringRedisAutoConfiguration.class },
    properties = { "spring.cache.type=GENERIC", "j2cache.open-spring-cache=true",
        "j2cache.j2CacheConfig.serialization=json", "j2cache.redis-client=jedis",
        "j2cache.cache-clean-mode=active", "j2cache.allow-null-values=true", "j2cache.l2-cache-open=true" })
public class J2CacheApplicationTests {

    @Autowired
    private TestService testService;

    @Autowired
    private CacheChannel cacheChannel;

    @Test
    public void beanCache() {
        testService.reset();
        testService.evict();
        testService.testBean();
        TestBean b = testService.testBean();
        Integer a = b.getNum();
        Assert.isTrue(a == 1, "对象缓存未生效！");
    }

    @Test
    public void clearCache() {
        testService.reset();
        testService.getNum();
        testService.reset();
        testService.evict();
        Integer a = testService.getNum();
        Assert.isTrue(a == 1, "清除缓存未生效！");
    }

    @Test
    public void test() {
        cacheChannel.set("test", "123", "321");
        CacheObject a = cacheChannel.get("test", "123");
        Assert.isTrue(a.getValue().equals("321"), "失败！");
    }

    @Test
    public void test1() {
        // cacheChannel.set("test", "123", "321");
        CacheObject a = cacheChannel.get("test", "1233");
        Assert.isTrue(a.getValue().equals("321"), "失败！");
    }

    @Test
    public void testCache() throws IOException {
        testService.reset();
        testService.evict();
        testService.getNum();
        for (int i = 1; i < 200; i++) {
            new Thread(() -> {
                for (int j = 1; j < 1000; j++) {
                    Integer n = testService.getNum();
                    System.out.println(n);
                    try {
                        Random r = new Random();
                        Thread.sleep(r.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
