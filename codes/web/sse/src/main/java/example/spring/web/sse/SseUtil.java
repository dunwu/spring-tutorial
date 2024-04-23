package example.spring.web.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-04-16
 */
@Slf4j
public class SseUtil {

    public static final long SSE_TIMEOUT = 30000L;

    private static final AtomicInteger COUNT = new AtomicInteger(0);
    private static final Map<String, SseEmitter> SSE_MAP = new ConcurrentHashMap<>();

    public static synchronized SseEmitter connect(String key) {

        if (SSE_MAP.containsKey(key)) {
            return SSE_MAP.get(key);
        }

        try {
            SseEmitter sseEmitter = new SseEmitter(SSE_TIMEOUT);
            sseEmitter.onCompletion(handleCompletion(key));
            sseEmitter.onError(handleError(key));
            sseEmitter.onTimeout(handleTimeout(key));
            SSE_MAP.put(key, sseEmitter);
            COUNT.getAndIncrement();
            log.info("【SSE】创建连接成功！key: {}, 当前连接数：{}", key, COUNT.get());
            return sseEmitter;
        } catch (Exception e) {
            log.error("【SSE】创建连接异常！key: {}", key, e);
            return null;
        }
    }

    public static synchronized boolean close(String key) {
        SseEmitter sseEmitter = SSE_MAP.get(key);
        if (sseEmitter == null) {
            return false;
        }
        sseEmitter.complete();
        SSE_MAP.remove(key);
        COUNT.getAndDecrement();
        log.info("【SSE】key: {} 断开连接！当前连接数：{}", key, COUNT.get());
        return true;
    }

    private static Runnable handleCompletion(String key) {
        return () -> {
            log.info("【SSE】连接结束！key: {}", key);
            close(key);
        };
    }

    private static Consumer<Throwable> handleError(String key) {
        return t -> {
            log.warn("【SSE】连接异常！key: {}", key, t);
            close(key);
        };
    }

    private static Runnable handleTimeout(String key) {
        return () -> {
            log.info("【SSE】连接超时！key: {}", key);
            close(key);
        };
    }

    public static void send(String key, Object message) {
        if (SSE_MAP.containsKey(key)) {
            try {
                SseEmitter sseEmitter = SSE_MAP.get(key);
                sseEmitter.send(message);
            } catch (Exception e) {
                log.error("【SSE】发送消息异常！key: {}, message: {}", key, message, e);
                close(key);
            }
        } else {
            log.warn("【SSE】发送消息失败！key: {}, message: {}", key, message);
        }
    }

}
