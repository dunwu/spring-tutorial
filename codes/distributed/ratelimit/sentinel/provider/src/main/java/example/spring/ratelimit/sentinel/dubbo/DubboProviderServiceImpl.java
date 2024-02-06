package example.spring.ratelimit.sentinel.dubbo;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * Dubbo 限流示例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-02-05
 */
@Slf4j
@DubboService
public class DubboProviderServiceImpl implements DubboProviderService {

    @Override
    @SentinelResource(value = "Dubbo限流", blockHandler = "provideBlockHandler")
    public String provide(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "hello, " + name;
    }

    public String provideBlockHandler(String name, BlockException e) {
        log.warn("Dubbo限流 -> 请求限流", e);
        return null;
    }

}
