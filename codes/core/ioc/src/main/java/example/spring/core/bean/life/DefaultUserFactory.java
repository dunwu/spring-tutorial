package example.spring.core.bean.life;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 默认 {@link UserFactory} 实现
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

    // =====================================================================================
    // 通过 PostConstruct 和 PreDestroy 指定 Bean 的初始化/销毁方法
    // =====================================================================================

    // 使用 @PostConstruct 注解指定初始化方法
    @PostConstruct
    public void postConstruct() {
        System.out.println("@PostConstruct : UserFactory 初始化中...");
    }

    // 使用 @PreDestroy 注解指定销毁方法
    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy : UserFactory 销毁中...");
    }

    // =====================================================================================
    // 通过 InitializingBean 和 DisposableBean 指定 Bean 的初始化/销毁方法
    // =====================================================================================

    // 实现 InitializingBean 接口的 afterPropertiesSet() 方法来编写初始化方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet() : UserFactory 初始化中...");
    }

    // 实现 DisposableBean 接口的 destroy() 方法来编写销毁方法
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy() : UserFactory 销毁中...");
    }

    // =====================================================================================
    // 自定义初始化/销毁方法，需要通过以下方式指定：
    // XML 配置：<bean init-method="init" destroy="destroy" ... />
    // Java 注解：@Bean(initMethod = "init", destroyMethod = "destroy")
    // Java API：AbstractBeanDefinition#setInitMethodName(String) 和 AbstractBeanDefinition#setDestroyMethodName(String) 分别定义初始化和销毁方法
    // =====================================================================================

    public void doInit() {
        System.out.println("自定义初始化方法 initUserFactory() : UserFactory 初始化中...");
    }

    public void doDestroy() {
        System.out.println("自定义销毁方法 doDestroy() : UserFactory 销毁中...");
    }

    @Override
    public void finalize() throws Throwable {
        System.out.println("当前 DefaultUserFactory 对象正在被垃圾回收...");
    }

}
