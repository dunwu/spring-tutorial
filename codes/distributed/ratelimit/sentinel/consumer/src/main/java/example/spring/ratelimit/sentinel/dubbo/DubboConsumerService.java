package example.spring.ratelimit.sentinel.dubbo;

/**
 * Dubbo 限流、熔断示例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-02-05
 */
public interface DubboConsumerService {

    String consume(String name);

}
