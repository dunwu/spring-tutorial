# Spring IoC 容器示例

## [示例]通过 Java 注入 Bean

执行 `io.github.dunwu.spring.core.ioc.java.AnnotationBeanDemo01#main` 方法。
执行 `io.github.dunwu.spring.core.ioc.java.AnnotationBeanDemo02#main` 方法。

## [示例]Spring Bean 在 IoC 容器的生命周期示例

> 本 package 示例演示了 Spring Bean 生命周期。

执行 `io.github.dunwu.spring.core.bean.BeanLifeCycleDemo#main` 方法。

执行顺序如下：

- BeanFactoryPostProcessor
- BeanPostProcessor
- InstantiationAwareBeanPostProcessorAdapter#postProcessBeforeInstantiation
- InstantiationAwareBeanPostProcessorAdapter#postProcessPropertyValues
- BeanNameAware.setBeanName
- BeanFactoryAware.setBeanFactory
- BeanPostProcessor.postProcessBeforeInitialization
- InitializingBean.afterPropertiesSet
- `<bean>`的 init-method
- BeanPostProcessor.postProcessAfterInitialization
- InstantiationAwareBeanPostProcessorAdapter.postProcessAfterInitialization
- DiposibleBean.destory
- `<bean>`的 destroy-method
