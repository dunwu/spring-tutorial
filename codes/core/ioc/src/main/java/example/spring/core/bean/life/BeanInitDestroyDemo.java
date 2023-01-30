package example.spring.core.bean.life;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * {@link Bean} 初始化和销毁的三种方式示例
 *
 * <ul>
 *     <li>{@link Bean} 的 initMethod 和 destroyMethod</li>
 *     <li>{@link InitializingBean} 和 {@link DisposableBean}</li>
 *     <li>{@link PostConstruct} 和 {@link PreDestroy}</li>
 * </ul>
 *
 * @author peng.zhang
 * @date 2021-11-29
 */
@Configuration
public class BeanInitDestroyDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanInitDestroyDemo.class);
        System.out.println("BeanInitDestroyDemo 示例结束");
        context.close();
    }
    // 输出：
    // [Pojo 构造方法]
    // [Pojo 初始化方法]
    // [Pojo2 构造方法]
    // [Pojo2 初始化方法]
    // [Pojo3 构造方法]
    // [Pojo3 初始化方法]
    // BeanInitDestroyDemo 示例结束
    // [Pojo3 销毁方法]
    // [Pojo2 销毁方法]
    // [Pojo 销毁方法]

    /**
     * 通过 {@link Bean} 的 initMethod 和 destroyMethod 指定 Bean 的初始化/销毁方法
     */
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Pojo pojo() {
        return new Pojo();
    }

    /**
     * 通过 {@link InitializingBean} 和 {@link DisposableBean} 指定 Bean 的初始化/销毁方法
     */
    @Bean
    public Pojo2 pojo2() {
        return new Pojo2();
    }

    /**
     * 通过 {@link PostConstruct} 和 {@link PreDestroy} 指定 Bean 的初始化/销毁方法
     */
    @Bean
    public Pojo3 pojo3() {
        return new Pojo3();
    }

    public static class Pojo {

        public Pojo() {
            System.out.println("[Pojo 构造方法]");
        }

        public void init() {
            System.out.println("[Pojo 初始化方法]");
        }

        public void destroy() {
            System.out.println("[Pojo 销毁方法]");
        }

    }

    public static class Pojo2 implements InitializingBean, DisposableBean {

        public Pojo2() {
            System.out.println("[Pojo2 构造方法]");
        }

        @Override
        public void afterPropertiesSet() {
            System.out.println("[Pojo2 初始化方法]");
        }

        @Override
        public void destroy() {
            System.out.println("[Pojo2 销毁方法]");
        }

    }

    public static class Pojo3 {

        public Pojo3() {
            System.out.println("[Pojo3 构造方法]");
        }

        @PostConstruct
        public void init() {
            System.out.println("[Pojo3 初始化方法]");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("[Pojo3 销毁方法]");
        }

    }

}
