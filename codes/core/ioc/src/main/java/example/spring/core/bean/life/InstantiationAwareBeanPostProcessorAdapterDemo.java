package example.spring.core.bean.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * InstantiationAwareBeanPostProcessorAdapter 示例
 *
 * @author peng.zhang
 * @date 2021-11-30
 */
@Configuration
public class InstantiationAwareBeanPostProcessorAdapterDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(InstantiationAwareBeanPostProcessorAdapterDemo.class);
        System.out.println("InstantiationAwareBeanPostProcessorAdapterDemo 示例结束");
        context.close();
    }

    @Bean
    public Pojo pojo() {
        return new Pojo();
    }

    @Bean
    public MyInstantiationAwareBeanPostProcessor myInstantiationAwareBeanPostProcessor() {
        return new MyInstantiationAwareBeanPostProcessor();
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

    static class MyInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

        public MyInstantiationAwareBeanPostProcessor() {
            super();
            System.out.println("[InstantiationAwareBeanPostProcessorAdapter] construct");
        }

        /**
         * 接口方法、实例化Bean之前调用
         */
        @Override
        public Object postProcessBeforeInstantiation(Class beanClass, String beanName) throws BeansException {
            System.out.println("[InstantiationAwareBeanPostProcessorAdapter] postProcessBeforeInstantiation");
            return null;
        }

        /**
         * 接口方法、实例化Bean之后调用
         */
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("[InstantiationAwareBeanPostProcessorAdapter] postProcessAfterInitialization");
            return bean;
        }

    }

}
