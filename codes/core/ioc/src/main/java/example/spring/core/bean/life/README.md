# 说明

> 本 package 示例演示了 Spring Bean 生命周期。

执行 io.github.dunwu.spring.core.bean.BeanLifeCycleDemo#main 方法。

执行顺序如下：

- BeanFactoryPostProcessor
- BeanPostProcessor
- InstantiationAwareBeanPostProcessorAdapter#postProcessBeforeInstantiation
- InstantiationAwareBeanPostProcessorAdapter#postProcessPropertyValues
- BeanNameAware.setBeanName
- BeanFactoryAware.setBeanFactory
- BeanPostProcessor.postProcessBeforeInitialization
- InitializingBean.afterPropertiesSet
- `<bean>`的init-method
- BeanPostProcessor.postProcessAfterInitialization
- InstantiationAwareBeanPostProcessorAdapter.postProcessAfterInitialization
- DiposibleBean.destory
- `<bean>`的destroy-method