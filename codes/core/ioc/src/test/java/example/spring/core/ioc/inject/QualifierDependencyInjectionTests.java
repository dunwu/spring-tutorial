package example.spring.core.ioc.inject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring 依赖注入限定注入测试集
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-07
 */
@DisplayName("Spring 依赖注入限定注入测试集")
public class QualifierDependencyInjectionTests {

    @Test
    public void test() {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类） -> Spring Bean
        applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/ioc/DependencyInject.xml";
        // 加载 XML 资源，解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找 QualifierAnnotationDependencyInjectionDemo Bean
        QualifierAnnotationDependencyInjectionDemo demo =
            applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);

        // 期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        Assertions.assertThat(demo.user.getId()).isEqualTo(1);
        Assertions.assertThat(demo.user.getName()).isEqualTo("钝悟");

        // 期待输出 user Bean
        System.out.println("demo.namedUser = " + demo.namedUser);
        Assertions.assertThat(demo.user.getId()).isEqualTo(1);
        Assertions.assertThat(demo.user.getName()).isEqualTo("钝悟");

        // 期待输出 superUser user user1 user2 user3 user4
        System.out.println("demo.allUsers = " + demo.allUsers);
        Assertions.assertThat(demo.allUsers).isNotEmpty();
        Assertions.assertThat(demo.allUsers).hasSize(5);

        // 期待输出 user2 user3 user4
        System.out.println("demo.qualifiedUsers = " + demo.qualifiedUsers);
        Assertions.assertThat(demo.qualifiedUsers).isNotEmpty();
        Assertions.assertThat(demo.qualifiedUsers).hasSize(3);

        // 期待输出 user3 user4
        System.out.println("demo.groupedUsers = " + demo.groupedUsers);
        Assertions.assertThat(demo.groupedUsers).isNotEmpty();
        Assertions.assertThat(demo.groupedUsers).hasSize(2);

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }

}
