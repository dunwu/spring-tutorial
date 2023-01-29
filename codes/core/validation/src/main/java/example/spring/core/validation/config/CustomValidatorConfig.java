package example.spring.core.validation.config;

import example.spring.core.validation.ValidatorRule;
import example.spring.core.validation.annotation.ValidRule;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class CustomValidatorConfig implements ApplicationContextAware {

    Map<String, Object> ruleMap = null;

    private final Map<Annotation, ValidatorRule> rules = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ruleMap = applicationContext.getBeansWithAnnotation(ValidRule.class);
        // System.out.println(ruleMap);
    }

    public ValidatorRule findRule(Annotation annotation) {
        ValidatorRule validatorRule;
        if (rules.containsKey(annotation)) {
            validatorRule = rules.get(annotation);
        } else {
            ValidatorRule vr = findFormMap(annotation);
            if (vr != null) {
                rules.put(annotation, vr);
            }
            validatorRule = vr;
        }
        return validatorRule;
    }

    private ValidatorRule findFormMap(Annotation annotation) {
        for (Entry<String, Object> entry : ruleMap.entrySet()) {
            if (entry.getValue() != null && ((ValidatorRule) entry.getValue()).support(annotation)) {
                return (ValidatorRule) entry.getValue();
            }
        }
        return null;
    }

}
