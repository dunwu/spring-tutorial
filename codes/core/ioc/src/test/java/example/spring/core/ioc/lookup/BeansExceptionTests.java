package example.spring.core.ioc.lookup;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * BeansException 测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-07
 */
@DisplayName("BeansException 测试")
public class BeansExceptionTests {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    public void beforeEach() {
        // 创建 BeanFactory 容器
        applicationContext = new AnnotationConfigApplicationContext();
    }

    @AfterEach
    public void afterEach() {
        // 关闭应用上下文
        applicationContext.close();
    }

    @Test
    @DisplayName("测试 BeanCreationException")
    public void testBeanCreationException() {

        // 注册 BeanDefinition Bean Class 是一个 POJO 普通类，不过初始化方法回调时抛出异常
        BeanDefinitionBuilder
            beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(POJO.class);
        applicationContext.registerBeanDefinition("errorBean", beanDefinitionBuilder.getBeanDefinition());

        try {
            // 启动应用上下文
            applicationContext.refresh();
        } catch (RuntimeException e) {
            Assertions.assertThat(e instanceof BeanCreationException).isTrue();
        }
    }

    @Test
    @DisplayName("测试 BeanInstantiationException")
    public void testBeanInstantiationException() {

        // 注册 BeanDefinition Bean Class 是一个 CharSequence 接口
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(CharSequence.class);
        applicationContext.registerBeanDefinition("errorBean", beanDefinitionBuilder.getBeanDefinition());

        try {
            // 启动应用上下文
            applicationContext.refresh();
        } catch (RuntimeException e) {
            Assertions.assertThat(e instanceof BeanCreationException).isTrue();
            BeanCreationException beanCreationException = (BeanCreationException) e;
            Throwable cause = beanCreationException.getCause();
            Assertions.assertThat(cause instanceof BeanInstantiationException).isTrue();
        }
    }

    @Test
    @DisplayName("测试 NoUniqueBeanDefinitionException")
    public void testNoUniqueBeanDefinitionException() {

        // 将当前类 NoUniqueBeanDefinitionExceptionDemo 作为配置类（Configuration Class）
        applicationContext.register(BeansExceptionTests.class);
        // 启动应用上下文
        applicationContext.refresh();

        try {
            // 由于 Spring 应用上下文存在两个 String 类型的 Bean，通过单一类型查找会抛出异常
            applicationContext.getBean(String.class);
        } catch (RuntimeException e) {
            Assertions.assertThat(e instanceof NoUniqueBeanDefinitionException).isTrue();
            NoUniqueBeanDefinitionException noUniqueBeanDefinitionException = (NoUniqueBeanDefinitionException) e;
            System.err.printf(" Spring 应用上下文存在%d个 %s 类型的 Bean，具体原因：%s%n",
                noUniqueBeanDefinitionException.getNumberOfBeansFound(),
                String.class.getName(),
                noUniqueBeanDefinitionException.getMessage());
        }
    }

    public static class POJO implements InitializingBean {

        @PostConstruct // CommonAnnotationBeanPostProcessor
        public void init() throws Throwable {
            throw new Throwable("init() : For purposes...");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            throw new Exception("afterPropertiesSet() : For purposes...");
        }

    }

    @Bean
    public String bean1() {
        return "1";
    }

    @Bean
    public String bean2() {
        return "2";
    }

    @Bean
    public String bean3() {
        return "3";
    }

}
