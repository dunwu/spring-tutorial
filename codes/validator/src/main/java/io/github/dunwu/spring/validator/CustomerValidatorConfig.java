package io.github.dunwu.spring.validator;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CustomerValidatorConfig implements ApplicationContextAware {
    private Map<Annotation, ValidatorRule> rules = new ConcurrentHashMap<Annotation, ValidatorRule>();
    Map<String, Object> customerValidationRules = null;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        customerValidationRules = applicationContext.getBeansWithAnnotation(ValidRule.class);
        System.out.println(customerValidationRules);
    }

    private ValidatorRule findFormMap(Annotation annotation) {
        for (Entry<String, Object> entry : customerValidationRules.entrySet()) {
            if (entry.getValue() != null && ((ValidatorRule) entry.getValue()).support(annotation)) {
                return (ValidatorRule) entry.getValue();
            }
        }
        return null;
    }

    public ValidatorRule findRule(Annotation annotation) {
        ValidatorRule customerValidatorRule = null;
        if (!rules.containsKey(annotation)) {
            ValidatorRule cvr = findFormMap(annotation);
            if (cvr != null) {
                rules.put(annotation, cvr);
            }
            customerValidatorRule = cvr;
        }
        customerValidatorRule = rules.get(annotation);
        return customerValidatorRule;
    }
}
