package example.spring.ratelimit.sentinel;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2024-02-06
 */
@Configuration
public class SentinelConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    /**
     * 初始化流控规则
     */
    @PostConstruct
    public void initFlowRules() {
        FlowRule httpRule = new FlowRule()
            .setResource("Http限流")
            .setLimitApp("default")
            .as(FlowRule.class)
            .setCount(2)
            .setGrade(RuleConstant.FLOW_GRADE_QPS);
        FlowRule dubboRule = new FlowRule()
            .setResource("Dubbo限流")
            .setLimitApp("default")
            .as(FlowRule.class)
            .setCount(2)
            .setGrade(RuleConstant.FLOW_GRADE_QPS);
        List<FlowRule> rules = Arrays.asList(httpRule, dubboRule);
        FlowRuleManager.loadRules(rules);
    }

}
