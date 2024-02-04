package example.spring.ratelimit.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-01-30
 */
public class SentinelDemo {

    public static void main(String[] args) {
        // 定义规则
        initFlowRules();

        // 定义资源
        while (true) {
            // 1.5.0 版本开始可以利用 try-with-resources 特性
            // 资源名可使用任意有业务语义的字符串，比如方法名、接口名或其它可唯一标识的字符串。
            try (Entry entry = SphU.entry("HelloWorld")) {
                // 被保护的业务逻辑
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 资源访问阻止，被限流或被降级
                // 在此处进行相应的处理操作
                System.err.println("blocked!");
            }
        }
    }

    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
