package example.spring.core.ioc;

import example.spring.core.bean.entity.person.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-05
 */
@DisplayName("Spring IoC 容器测试")
@ExtendWith(SpringExtension.class)
public class IoCContainerTests {

    @Test
    @DisplayName("测试 BeanFactory")
    public void testBeanFactory() {
        // 创建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // XML 配置文件 ClassPath 路径
        int beanDefinitionsCount = reader.loadBeanDefinitions("classpath:/META-INF/ioc/DependencyInject.xml");
        System.out.println("Bean 定义加载的数量：" + beanDefinitionsCount);
        // 依赖查找集合对象
        Map<String, User> users = beanFactory.getBeansOfType(User.class);
        Assertions.assertThat(users).isNotEmpty();
        System.out.println("查找到的所有的 User 集合对象：" + users);
    }

    @Test
    @DisplayName("测试 ApplicationContext")
    public void testApplicationContext() {
        // XML 配置文件 ClassPath 路径
        ClassPathXmlApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/ioc/DependencyInject.xml");
        // 启动应用上下文
        applicationContext.refresh();
        // 依赖查找集合对象
        Map<String, User> users = applicationContext.getBeansOfType(User.class);
        Assertions.assertThat(users).isNotEmpty();
        System.out.println("查找到的所有的 User 集合对象：" + users);
        // 关闭应用上下文
        applicationContext.close();
    }

}
