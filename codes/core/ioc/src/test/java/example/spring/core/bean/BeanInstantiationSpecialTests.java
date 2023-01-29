package example.spring.core.bean;

import cn.hutool.json.JSONUtil;
import example.spring.core.bean.entity.person.User;
import example.spring.core.bean.life.DefaultUserFactory;
import example.spring.core.bean.life.UserFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 特殊方式实例化 Spring Bean 测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-06
 */
@DisplayName("特殊方式实例化 Spring Bean 测试")
public class BeanInstantiationSpecialTests {

    @Test
    @DisplayName("通过 AutowireCapableBeanFactory#createBean 实例化 Bean")
    public void testBeanInstantiationByAutowireCapableBeanFactory() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/bean/BeanInstantiationSpecial.xml");

        // 通过 ApplicationContext 获取 AutowireCapableBeanFactory
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        // 创建 UserFactory 对象，通过 AutowireCapableBeanFactory
        UserFactory userFactory = beanFactory.createBean(DefaultUserFactory.class);
        User user = userFactory.createUser();
        Assertions.assertThat(user).isNotNull();
        System.out.println("user: " + JSONUtil.toJsonStr(user));
    }

    @Test
    @DisplayName("通过 ServiceLoader （Spring SPI）实例化 Bean 测试例一")
    public void testBeanInstantiationByServiceLoader() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/bean/BeanInstantiationSpecial.xml");

        // 通过 ApplicationContext 获取 AutowireCapableBeanFactory
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        // 1. 添加 /META-INF/services/io.github.dunwu.spring.core.bean.life.UserFactory 文件
        // 2. 文件中指定 UserFactory 的实现类（io.github.dunwu.spring.core.bean.life.DefaultUserFactory）
        // 3. 获取 ServiceLoader

        // 通过 AutowireCapableBeanFactory 获取 ServiceLoader
        ServiceLoader<UserFactory> serviceLoader = beanFactory.getBean("userFactoryServiceLoader", ServiceLoader.class);

        // 使用 ServiceLoader 获取 UserFactory 实例
        Iterator<UserFactory> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            UserFactory userFactory = iterator.next();
            User user = userFactory.createUser();
            Assertions.assertThat(user).isNotNull();
            System.out.println("user: " + JSONUtil.toJsonStr(user));
        }
    }

    @Test
    @DisplayName("通过 ServiceLoader （Spring SPI）实例化 Bean 测试例二")
    public void testBeanInstantiationByServiceLoader2() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/bean/BeanInstantiationSpecial.xml");

        // 通过 ApplicationContext 获取 AutowireCapableBeanFactory
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        // 1. 添加 /META-INF/services/io.github.dunwu.spring.core.bean.life.UserFactory 文件
        // 2. 文件中指定 UserFactory 的实现类（io.github.dunwu.spring.core.bean.life.DefaultUserFactory）
        // 3. 获取 ServiceLoader

        // 通过 ServiceLoader.load 获取 ServiceLoader
        ServiceLoader<UserFactory> serviceLoader =
            ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());

        // 使用 ServiceLoader 获取 UserFactory 实例
        Iterator<UserFactory> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            UserFactory userFactory = iterator.next();
            User user = userFactory.createUser();
            Assertions.assertThat(user).isNotNull();
            System.out.println("user: " + JSONUtil.toJsonStr(user));
        }
    }

}
