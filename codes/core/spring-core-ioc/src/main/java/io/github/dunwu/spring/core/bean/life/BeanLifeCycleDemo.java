package io.github.dunwu.spring.core.bean.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Spring Bean 一个较为完整的生命周期流程走向
 */
@Component
public class BeanLifeCycleDemo {

    public static void main(String[] args) {
        // 注解配置方式
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanLifeCycleDemo.class);
        System.out.println("BeanLifeCycleDemo 示例结束");

        // XML 配置方式
        // String path = "META-INF/bean/life/BeanLifeCycleDemo.xml";
        // ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(path);

        // 关闭应用上下文容器
        context.close();
    }

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public BeanLifecycle beanLifecycle() {
        return new BeanLifecycle();
    }

    public static class BeanLifecycle implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,
        BeanPostProcessor, InitializingBean, DisposableBean {

        @Override
        public void setBeanName(String beanName) {
            System.out.println("setBeanName -> " + beanName);
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            System.out.println("setBeanFactory -> beanFactory");
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            System.out.println("setApplicationContext -> applicationContext");
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("postProcessBeforeInitialization -> " + beanName);
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("postProcessAfterInitialization -> " + beanName);
            return bean;
        }

        @Override
        public void afterPropertiesSet() {
            System.out.println("afterPropertiesSet");
        }

        @Override
        public void destroy() {
            System.out.println("destroy");
        }

        /**
         * 通过<bean>的init-method属性指定的初始化方法
         */
        public void initMethod() {
            System.out.println("<bean> init-method");
        }

        /**
         * 通过<bean>的destroy-method属性指定的初始化方法
         */
        public void destroyMethod() {
            System.out.println("<bean> 的 destroy-method");
        }

    }

}
