package example.spring.core.ioc.lookup;

import example.spring.core.bean.entity.person.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-06
 */
public class TypeSafetyDependencyLookupTests {

    private AnnotationConfigApplicationContext applicationContext;

    @BeforeEach
    public void beforeEach() {
        // 创建 BeanFactory 容器
        applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 TypeSafetyDependencyLookupDemo 作为配置类（Configuration Class）
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
    }

    @AfterEach
    public void afterEach() {
        // 关闭应用上下文
        applicationContext.close();
    }

    @Test
    @DisplayName("演示 BeanFactory#getBean 方法的安全性")
    public void test() {
        try {
            applicationContext.getBean(User.class);
        } catch (Exception e) {
            Assertions.assertThat(e instanceof NoSuchBeanDefinitionException).isTrue();
        }
    }

    @Test
    @DisplayName("演示 ObjectFactory#getObject 方法的安全性")
    public void test2() {
        try {
            ObjectFactory<User> beanProvider = applicationContext.getBeanProvider(User.class);
            User user = beanProvider.getObject();
        } catch (Exception e) {
            Assertions.assertThat(e instanceof NoSuchBeanDefinitionException).isTrue();
        }
    }

    @Test
    @DisplayName("演示 ObjectProvider#getIfAvaiable 方法的安全性")
    public void test3() {
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
        User user = beanProvider.getIfAvailable();
        Assertions.assertThat(user).isNull();
    }

    @Test
    @DisplayName("演示 ListableBeanFactory#getBeansOfType 方法的安全性")
    public void test4() {
        Map<String, User> beansOfType = applicationContext.getBeansOfType(User.class);
        Assertions.assertThat(beansOfType).isEmpty();
    }

    @Test
    @DisplayName("演示 ObjectProvider Stream 操作的安全性")
    public void test5() {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        Assertions.assertThat(userObjectProvider).isEmpty();
        userObjectProvider.forEach(System.out::println);
    }

}
