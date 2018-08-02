package io.github.dunwu.spring.bean;

import java.beans.PropertyDescriptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

public class MyInstantiationAwareBeanPostProcessor extends
    InstantiationAwareBeanPostProcessorAdapter {

    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out.println("[InstantiationAwareBeanPostProcessorAdapter] construct");
    }

    // 接口方法、实例化Bean之前调用
    @Override
    public Object postProcessBeforeInstantiation(Class beanClass, String beanName) throws BeansException {
        System.out.println("[InstantiationAwareBeanPostProcessorAdapter] call postProcessBeforeInstantiation");
        return null;
    }

    // 接口方法、实例化Bean之后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
        throws BeansException {
        System.out.println("[InstantiationAwareBeanPostProcessorAdapter] call postProcessAfterInitialization");
        return bean;
    }

    // 接口方法、设置某个属性时调用
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean,
        String beanName)
        throws BeansException {
        System.out.println("[InstantiationAwareBeanPostProcessorAdapter] call postProcessPropertyValues");
        return pvs;
    }
}
