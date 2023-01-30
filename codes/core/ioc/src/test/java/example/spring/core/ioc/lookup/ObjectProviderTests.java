package example.spring.core.ioc.lookup;

import cn.hutool.json.JSONUtil;
import example.spring.core.bean.entity.person.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 通过 ObjectProvider 延迟依赖查找
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-06
 */
public class ObjectProviderTests {

    @Test
    @DisplayName("通过 ObjectProvider 延迟依赖查找")
    public void testObjectProvider() {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 ObjectProviderDemo 作为配置类（Configuration Class）
        applicationContext.register(ObjectProviderDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 依赖查找集合对象
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        Assertions.assertThat(objectProvider.getObject()).isEqualTo("Hello,World");
        System.out.println(objectProvider.getObject());
        // 关闭应用上下文
        applicationContext.close();
    }

    @Test
    @DisplayName("通过 ObjectProvider.getIfAvailable 延迟依赖查找")
    public void testGetIfAvailable() {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 ObjectProviderDemo 作为配置类（Configuration Class）
        applicationContext.register(ObjectProviderDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 依赖查找集合对象
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        User user = objectProvider.getIfAvailable(User::createUser);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getName()).isEqualTo("钝悟");
        System.out.println("user: " + JSONUtil.toJsonStr(user));
        // 关闭应用上下文
        applicationContext.close();
    }

    @Test
    @DisplayName("通过 ObjectProvider stream 延迟依赖查找")
    public void testGetIfAvailable2() {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 ObjectProviderDemo 作为配置类（Configuration Class）
        applicationContext.register(ObjectProviderDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 依赖查找集合对象
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        // Stream -> Method reference
        Assertions.assertThat(objectProvider).contains("Hello,World", "Message");
        objectProvider.stream().forEach(System.out::println);
        // 关闭应用上下文
        applicationContext.close();
    }

}
