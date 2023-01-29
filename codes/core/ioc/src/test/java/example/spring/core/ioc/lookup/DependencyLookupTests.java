package example.spring.core.ioc.lookup;

import example.spring.core.bean.annotation.Super;
import example.spring.core.bean.entity.person.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

/**
 * Spring 依赖查找测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-05
 */
@DisplayName("Spring 依赖查找测试")
@ExtendWith(SpringExtension.class)
public class DependencyLookupTests {

    private BeanFactory beanFactory;

    @BeforeEach
    public void initBeanFactory() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        beanFactory =
            new ClassPathXmlApplicationContext("classpath:/META-INF/ioc/DependencyLookup.xml");
    }

    @Test
    @DisplayName("根据名称查找 Bean")
    public void testLookupByName() {
        User user = (User) beanFactory.getBean("user");
        Assertions.assertThat(user).isNotNull();
        System.out.println("User：" + user);

        User superUser = (User) beanFactory.getBean("superUser");
        Assertions.assertThat(superUser).isNotNull();
        System.out.println("User：" + superUser);
    }

    @Test
    @DisplayName("根据类型查找 Bean")
    public void testLookupByType() {
        User user = beanFactory.getBean(User.class);
        Assertions.assertThat(user).isNotNull();
        System.out.println("User：" + user);
    }

    @Test
    @DisplayName("根据名称和类型查找 Bean")
    public void testLookupByNameAndType() {
        User user = beanFactory.getBean("user", User.class);
        Assertions.assertThat(user).isNotNull();
        System.out.println("User：" + user);
    }

    @Test
    @DisplayName("根据注解查找 Bean")
    public void lookupByAnnotationType() {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            Assertions.assertThat(users).isNotEmpty();
            System.out.println("查找标注 @Super 所有的 User 集合对象：" + users);
        }
    }

    @Test
    @DisplayName("根据集合查找 Bean")
    public void lookupCollectionByType() {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            Assertions.assertThat(users).isNotEmpty();
            System.out.println("查找到的所有的 User 集合对象：" + users);
        }
    }

    @Test
    @DisplayName("延迟查找 Bean")
    public void lookupInLazy() {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        Assertions.assertThat(user).isNotNull();
        System.out.println("延迟查找：" + user);
    }

}
