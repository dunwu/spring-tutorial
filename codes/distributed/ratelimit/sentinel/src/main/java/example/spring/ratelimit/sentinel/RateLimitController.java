package example.spring.ratelimit.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 * 限流示例 Http 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-01-30
 */
@Slf4j
@RestController
public class RateLimitController {

    /**
     * 抛出异常的方式定义资源并限流
     */
    @GetMapping("/limit1")
    public String limit1() {
        // 1.5.0 版本开始可以利用 try-with-resources 特性
        // 资源名可使用任意有业务语义的字符串，比如方法名、接口名或其它可唯一标识的字符串。
        try (Entry entry = SphU.entry("limit1")) {
            // 被保护的业务逻辑
            log.info("limit1 -> 请求通过");
            return "ok";
        } catch (BlockException e) {
            // 资源访问阻止，被限流或被降级
            // 在此处进行相应的处理操作
            log.error("limit1 -> 请求限流", e);
            return "failed";
        }
    }

    /**
     * 返回布尔值方式定义资源并限流
     */
    @GetMapping("/limit2")
    public String limit2() {
        // 资源名可使用任意有业务语义的字符串
        if (SphO.entry("limit2")) {
            // 务必保证finally会被执行
            try {
                // 被保护的业务逻辑
                log.info("limit2 -> 请求通过");
                return "ok";
            } finally {
                SphO.exit();
            }
        } else {
            // 资源访问阻止，被限流或被降级
            // 进行相应的处理操作
            log.error("limit2 -> 请求限流");
            return "failed";
        }
    }

    /**
     * 注解方式定义资源并限流
     */
    @GetMapping("/limit3")
    @SentinelResource(value = "limit3")
    public String limit3() {
        try {
            log.info("limit3 -> 请求通过");
            return "ok";
        } catch (Exception e) {
            log.error("limit3 -> 请求限流", e);
            return "failed";
        }
    }

    /**
     * 初始化流控规则
     */
    @PostConstruct
    public void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置限流资源
        rule.setResource("limit1");
        // 设置 QPS
        rule.setCount(2);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
