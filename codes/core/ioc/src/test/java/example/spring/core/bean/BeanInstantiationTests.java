package example.spring.core.bean;

import cn.hutool.json.JSONUtil;
import example.spring.core.bean.entity.person.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 常规方式实例化 Spring Bean 测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-06
 */
@DisplayName("常规方式实例化 Spring Bean 测试")
public class BeanInstantiationTests {

    @Test
    @DisplayName("通过静态方法实例化 Bean")
    public void testBeanInstantiationByConstructor() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/bean/BeanInstantiation.xml");

        User user = applicationContext.getBean("user", User.class);
        Assertions.assertThat(user).isNotNull();
        System.out.println("user: " + JSONUtil.toJsonStr(user));
    }

    @Test
    @DisplayName("通过静态方法实例化 Bean")
    public void testBeanInstantiationByStaticMethod() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/bean/BeanInstantiation.xml");

        User user = applicationContext.getBean("user-by-static-method", User.class);
        Assertions.assertThat(user.getId()).isEqualTo(1);
        Assertions.assertThat(user.getName()).isEqualTo("钝悟");
        System.out.println("user: " + JSONUtil.toJsonStr(user));
    }

    @Test
    @DisplayName("通过 Bean 工厂方法实例化 Bean")
    public void testBeanInstantiationByBeanFactoryMethod() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/bean/BeanInstantiation.xml");

        User user = applicationContext.getBean("user-by-bean-factory-method", User.class);
        Assertions.assertThat(user.getId()).isEqualTo(1);
        Assertions.assertThat(user.getName()).isEqualTo("钝悟");
        System.out.println("user: " + JSONUtil.toJsonStr(user));
    }

    @Test
    @DisplayName("通过 FactoryBean 实例化 Bean")
    public void testBeanInstantiationByFactoryBean() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/bean/BeanInstantiation.xml");

        User user = applicationContext.getBean("user-by-factory-bean", User.class);
        Assertions.assertThat(user.getId()).isEqualTo(1);
        Assertions.assertThat(user.getName()).isEqualTo("钝悟");
        System.out.println("user: " + JSONUtil.toJsonStr(user));
    }

    @Test
    @DisplayName("测试不同方式实例化的 Bean 是否相等")
    public void test() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/bean/BeanInstantiation.xml");

        User user = applicationContext.getBean("user", User.class);
        User user2 = applicationContext.getBean("user-by-static-method", User.class);
        User user3 = applicationContext.getBean("user-by-bean-factory-method", User.class);
        User user4 = applicationContext.getBean("user-by-factory-bean", User.class);

        Assertions.assertThat(user).isNotEqualTo(user2);
        Assertions.assertThat(user).isNotEqualTo(user3);
        Assertions.assertThat(user).isNotEqualTo(user4);

        Assertions.assertThat(user2).isNotEqualTo(user3);
        Assertions.assertThat(user2).isNotEqualTo(user4);

        Assertions.assertThat(user3).isNotEqualTo(user4);
    }

}
