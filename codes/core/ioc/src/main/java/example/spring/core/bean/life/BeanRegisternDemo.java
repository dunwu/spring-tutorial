package example.spring.core.bean.life;

import example.spring.core.bean.entity.person.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 注解方式注册 Bean 示例
 * <p>
 * 注意：{@link Config} 被标记为 {@link Configuration}，并被 {@link Import} 导入，但是不会创建两个 Bean
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
@Import(BeanRegisternDemo.Config.class)
public class BeanRegisternDemo {

    /**
     * 2. 定义一个 Spring 配置类
     */
    @Configuration
    public static class Config {

        /**
         * 1. 通过 Java 注解的方式（@Bean），定义了一个 Bean
         */
        @Bean(name = { "user", "dunwu" })
        public User user() {
            User user = new User();
            user.setId(1L);
            user.setName("钝悟");
            return user;
        }

    }

}
