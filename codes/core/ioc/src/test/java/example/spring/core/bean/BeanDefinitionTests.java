package example.spring.core.bean;

import example.spring.core.bean.entity.person.City;
import example.spring.core.bean.entity.person.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试 Spring Bean 定义
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-06
 */
@DisplayName("测试 Spring Bean 定义")
public class BeanDefinitionTests {

    @Test
    @DisplayName("通过 BeanDefinitionBuilder 构建 BeanDefinition")
    public void testBeanDefinitionBuilder() {
        // 通过 BeanDefinitionBuilder 构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 通过属性设置
        beanDefinitionBuilder
            .addPropertyValue("id", 1)
            .addPropertyValue("name", "钝悟");
        // 获取 BeanDefinition 实例
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // BeanDefinition 并非 Bean 终态，可以自定义修改
        Assertions.assertThat(beanDefinition).isNotNull();
    }

    @Test
    @DisplayName("通过 AbstractBeanDefinition 以及派生类构建 BeanDefinition")
    public void testAbstractBeanDefinition() {
        // 通过 AbstractBeanDefinition 以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        // 设置 Bean 类型
        genericBeanDefinition.setBeanClass(User.class);
        // 通过 MutablePropertyValues 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues
            .add("id", 1)
            .add("name", "钝悟");
        // 通过 set MutablePropertyValues 批量操作属性
        genericBeanDefinition.setPropertyValues(propertyValues);
    }

    @Test
    @DisplayName("Bean 别名测试")
    public void testBeanAlias() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/bean/BeanAlias.xml");

        // 通过别名 user-dunwu 获取曾用名 user 的 bean
        User user = applicationContext.getBean("user", User.class);
        Assertions.assertThat(user.getId()).isEqualTo(1);
        Assertions.assertThat(user.getName()).isEqualTo("钝悟");
        Assertions.assertThat(user.getCity()).isEqualTo(City.SHANGHAI);

        User aliasUser = applicationContext.getBean("aliasUser", User.class);
        Assertions.assertThat(aliasUser.getId()).isEqualTo(1);
        Assertions.assertThat(aliasUser.getName()).isEqualTo("钝悟");
        Assertions.assertThat(aliasUser.getCity()).isEqualTo(City.SHANGHAI);

        Assertions.assertThat(user).isEqualTo(aliasUser);
        System.out.println("superUser 是否与 adminUser Bean 相同：" + (user == aliasUser));
    }

}
