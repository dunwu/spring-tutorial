package example.spring.ratelimit.sentinel.dubbo;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * Dubbo 限流、熔断示例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-02-05
 */
@Slf4j
@Service
public class DubboConsumerServiceImpl implements DubboConsumerService {

    @DubboReference(retries = 0, timeout = 1000)
    private DubboProviderService dubboProviderService;

    @Override
    @SentinelResource(value = "Dubbo熔断", blockHandler = "consumeBlockHandler")
    public String consume(String name) {
        try {
            return dubboProviderService.provide(name);
        } catch (Exception e) {
            log.error("Dubbo 调用异常");
            throw new RuntimeException(e);
        }
    }

    public String consumeBlockHandler(String name, BlockException e) {
        log.warn("Dubbo熔断 -> 请求熔断", e);
        return null;
    }

}
