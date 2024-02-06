package example.spring.ratelimit.sentinel.demo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 抛出异常的方式定义资源
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-01-30
 */
@Slf4j
public class BaseSentinelDemo {

    public static void main(String[] args) {

        // 定义规则
        initFlowRules();

        int count = 0;

        // 定义资源
        while (count < 100) {
            count++;
            // 1.5.0 版本开始可以利用 try-with-resources 特性
            // 资源名可使用任意有业务语义的字符串，比如方法名、接口名或其它可唯一标识的字符串。
            try (Entry entry = SphU.entry("限流1")) {
                // 被保护的业务逻辑
                log.info("限流1 -> 请求通过");
            } catch (BlockException e) {
                // 资源访问阻止，被限流或被降级
                // 在此处进行相应的处理操作
                log.error("限流1 -> 请求限流", e);
            }
        }
    }

    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("限流1");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
