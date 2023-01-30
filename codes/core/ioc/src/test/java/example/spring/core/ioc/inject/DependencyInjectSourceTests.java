package example.spring.core.ioc.inject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 测试依赖注入来源
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-05
 */
@DisplayName("测试依赖注入来源")
public class DependencyInjectSourceTests {

    @Test
    @DisplayName("依赖来源一：自定义 Bean")
    public void test() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/ioc/DependencyInjectSource.xml");

        // 依赖来源一：自定义 Bean
        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);
        Assertions.assertThat(userRepository.getUsers()).isNotNull();
        System.out.println(userRepository.getUsers());
    }

    @Test
    @DisplayName("依赖来源二：依赖注入（內建依赖）")
    public void test2() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/ioc/DependencyInjectSource.xml");

        UserRepository userRepository = applicationContext.getBean("userRepository2", UserRepository.class);
        Assertions.assertThat(userRepository.getUsers()).isNotNull();
        System.out.println(userRepository.getUsers());

        // 依赖来源二：依赖注入（內建依赖）
        System.out.println(userRepository.getBeanFactory());

        ObjectFactory<ApplicationContext> userFactory = userRepository.getObjectFactory();
        System.out.println(userFactory.getObject() == applicationContext);

        // 依赖查找（错误）
        // System.out.println(beanFactory.getBean(BeanFactory.class));
    }

    @Test
    @DisplayName("依赖来源三：容器內建 Bean")
    public void test3() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/ioc/DependencyInject.xml");

        // 依赖来源三：容器內建 Bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 类型的 Bean：" + environment);
    }

}
