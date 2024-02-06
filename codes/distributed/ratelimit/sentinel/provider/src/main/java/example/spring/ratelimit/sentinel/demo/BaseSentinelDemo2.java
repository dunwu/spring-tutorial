package example.spring.ratelimit.sentinel.demo;

import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回布尔值方式定义资源并限流
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-01-30
 */
@Slf4j
public class BaseSentinelDemo2 {

    public static void main(String[] args) {

        // 定义规则
        initFlowRules();

        int count = 0;

        // 定义资源
        while (count < 100) {
            count++;
            // 资源名可使用任意有业务语义的字符串
            if (SphO.entry("限流2")) {
                // 务必保证finally会被执行
                try {
                    // 被保护的业务逻辑
                    log.info("限流2 -> 请求通过");
                } finally {
                    SphO.exit();
                }
            } else {
                // 资源访问阻止，被限流或被降级
                // 进行相应的处理操作
                log.error("限流2 -> 请求限流");
            }
        }
    }

    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("限流2");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
