package example.spring.ratelimit.sentinel;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DubboRateLimitTest {

    public static final String HOST = "http://localhost:8082";

    @Test
    @DisplayName("Dubbo 限流测试")
    public void limitDubboTest() {
        final int threadNum = 30;
        final CountDownLatch latch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            CompletableFuture.runAsync(() -> {
                try {
                    String result = HttpUtil.get(HOST + "/limit/dubbo?name=" + RandomUtil.randomString(3));
                    System.out.println(result);
                } catch (Exception e) {
                    log.error("发生异常!", e);
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("发生异常!", e);
        }
    }

}
