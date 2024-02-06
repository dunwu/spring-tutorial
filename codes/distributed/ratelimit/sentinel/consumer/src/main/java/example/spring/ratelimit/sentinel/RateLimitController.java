package example.spring.ratelimit.sentinel;

import example.spring.ratelimit.sentinel.dubbo.DubboConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dubbo 限流示例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-02-05
 */
@RestController
public class RateLimitController {

    @Autowired
    private DubboConsumerService dubboConsumerService;

    @GetMapping("/limit/dubbo")
    public String consume(@RequestParam("name") String name) {
        return dubboConsumerService.consume(name);
    }

}
