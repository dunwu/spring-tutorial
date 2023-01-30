package example.spring.core.bean.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BeanPostProcessor 示例
 *
 * @author peng.zhang
 * @date 2021-11-30
 */
@Configuration
public class BeanPostProcessorDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(BeanPostProcessorDemo.class);
        System.out.println("BeanPostProcessorDemo 示例结束");
        context.close();
        // 输出
        // [BeanPostProcessor] construct
        // [Pojo 构造方法]
        // [BeanPostProcessor] postProcessBeforeInitialization
        // [Pojo 初始化方法]
        // [BeanPostProcessor] postProcessAfterInitialization
        // BeanPostProcessorDemo 示例结束
        // [Pojo 销毁方法]
    }

    @Bean
    public Pojo pojo() {
        return new Pojo();
    }

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }

    static class Pojo implements InitializingBean, DisposableBean {

        public Pojo() {
            System.out.println("[Pojo 构造方法]");
        }

        @Override
        public void afterPropertiesSet() {
            System.out.println("[Pojo 初始化方法]");
        }

        @Override
        public void destroy() {
            System.out.println("[Pojo 销毁方法]");
        }

    }

    static class MyBeanPostProcessor implements BeanPostProcessor {

        public MyBeanPostProcessor() {
            System.out.println("[BeanPostProcessor] construct");
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("[BeanPostProcessor] postProcessBeforeInitialization");
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("[BeanPostProcessor] postProcessAfterInitialization");
            return bean;
        }

    }

}
