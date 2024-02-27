package example.spring.ratelimit.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @RequestMapping("/flow/limit")
    @SentinelResource(value = "流量控制", blockHandler = "flowLimitBlockHandler")
    public String flowLimit() {
        try {
            log.info("流量控制 -> 请求通过");
            return "ok";
        } catch (Exception e) {
            log.error("流量控制 -> 请求异常", e);
            return "failed";
        }
    }

    public String flowLimitBlockHandler(BlockException e) {
        log.warn("流量控制 -> 请求限流", e);
        return "failed";
    }

    @RequestMapping("/param/flow/limit")
    @SentinelResource(value = "热点参数流量控制", blockHandler = "paramFlowLimitBlockHandler")
    public String paramFlowLimit(@RequestParam("key1") String key1, @RequestParam("key2") Integer key2) {
        try {
            log.info("热点参数流量控制 -> 请求通过");
            return "ok";
        } catch (Exception e) {
            log.error("热点参数流量控制 -> 请求异常", e);
            return "failed";
        }
    }

    public String paramFlowLimitBlockHandler(String key1, Integer key2, BlockException e) {
        log.warn("热点参数流量控制 -> 请求限流", e);
        return "failed";
    }

}
