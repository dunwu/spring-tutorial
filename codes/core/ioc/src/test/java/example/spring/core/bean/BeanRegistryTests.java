package example.spring.core.bean;

import example.spring.core.bean.entity.person.User;
import example.spring.core.bean.life.BeanRegisternDemo;
import example.spring.core.bean.life.DefaultUserFactory;
import example.spring.core.bean.life.UserFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

import java.util.Map;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

/**
 * 测试 Spring Bean 注册
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-06
 */
@DisplayName("测试 Spring Bean 注册")
public class BeanRegistryTests {

    @Test
    @DisplayName("注解方式注册 Spring Bean 测试例")
    public void testBeanRegistryByAnnotation() {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 创建一个外部 UserFactory 对象
        UserFactory userFactory = new DefaultUserFactory();
        SingletonBeanRegistry singletonBeanRegistry = applicationContext.getBeanFactory();
        // 注册外部单例对象
        singletonBeanRegistry.registerSingleton("userFactory", userFactory);
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 通过依赖查找的方式来获取 UserFactory
        UserFactory userFactoryByLookup = applicationContext.getBean("userFactory", UserFactory.class);
        System.out.println("userFactory  == userFactoryByLookup : " + (userFactory == userFactoryByLookup));
        Assertions.assertThat(userFactory).isEqualTo(userFactoryByLookup);

        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    @Test
    @DisplayName("注解方式注册 Spring Bean 测试例2——通过 @Import 导入配置类，扫描注解注册 Spring Bean")
    public void testBeanRegistryByAnnotation2() {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类）
        applicationContext.register(BeanRegisternDemo.class);

        // 通过 BeanDefinition 注册 API 实现
        // 1.命名 Bean 的注册方式
        registerUserBeanDefinition(applicationContext, "user");
        // 2.非命名 Bean 的注册方法
        registerUserBeanDefinition(applicationContext, null);

        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 按照类型依赖查找
        Map<String, BeanRegisternDemo.Config> beans =
            applicationContext.getBeansOfType(BeanRegisternDemo.Config.class);
        Assertions.assertThat(beans).isNotEmpty();
        Assertions.assertThat(beans).hasSize(1);
        System.out.println("Config 类型的所有 Beans" + beans);

        Map<String, User> beans2 = applicationContext.getBeansOfType(User.class);
        Assertions.assertThat(beans2).isNotEmpty();
        Assertions.assertThat(beans2).hasSize(2);
        System.out.println("User 类型的所有 Beans" + beans2);

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

    public void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = genericBeanDefinition(User.class);
        beanDefinitionBuilder
            .addPropertyValue("id", 1L)
            .addPropertyValue("name", "钝悟");

        // 判断如果 beanName 参数存在时
        if (StringUtils.hasText(beanName)) {
            // 注册 BeanDefinition
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            // 非命名 Bean 注册方法
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }
    }

}
