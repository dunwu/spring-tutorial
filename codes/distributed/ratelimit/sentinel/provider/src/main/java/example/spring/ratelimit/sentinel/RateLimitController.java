package example.spring.ratelimit.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Http 限流示例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-01-30
 */
@Slf4j
@RestController
public class RateLimitController {

    /**
     * 注解方式定义资源并限流
     */
    @GetMapping("/limit/http")
    @SentinelResource(value = "Http限流", blockHandler = "httpLimitBlockHandler")
    public String limitHttp() {
        try {
            log.info("Http限流 -> 请求通过");
            return "ok";
        } catch (Exception e) {
            log.error("Http限流 -> 请求异常", e);
            return "failed";
        }
    }

    public String httpLimitBlockHandler(BlockException e) {
        log.warn("Http限流 -> 请求限流", e);
        return "failed";
    }

    @GetMapping("/limit/http")
    @SentinelResource(value = "Http限流", blockHandler = "httpLimitBlockHandler")
    public void paramFlowControl() {

    }

}
