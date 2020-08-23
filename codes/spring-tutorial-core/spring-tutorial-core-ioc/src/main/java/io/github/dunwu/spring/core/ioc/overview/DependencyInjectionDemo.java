package io.github.dunwu.spring.core.ioc.overview;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入示例
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        //        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "classpath:/META-INF/dependency-injection-context.xml");

        // 依赖来源一：自定义 Bean
        UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);

        //        System.out.println(userRepository.getUsers());

        // 依赖来源二：依赖注入（內建依赖）
        System.out.println(userRepository.getBeanFactory());

        ObjectFactory userFactory = userRepository.getObjectFactory();

        System.out.println(userFactory.getObject() == applicationContext);

        // 依赖查找（错误）
        //        System.out.println(beanFactory.getBean(BeanFactory.class));

        // 依赖来源三：容器內建 Bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 Environment 类型的 Bean：" + environment);
    }

    private static void whoIsIoCContainer(UserRepository userRepository, ApplicationContext applicationContext) {

        // ConfigurableApplicationContext <- ApplicationContext <- BeanFactory

        // ConfigurableApplicationContext#getBeanFactory()

        // 这个表达式为什么不会成立
        System.out.println(userRepository.getBeanFactory() == applicationContext);

        // ApplicationContext is BeanFactory

    }

}
