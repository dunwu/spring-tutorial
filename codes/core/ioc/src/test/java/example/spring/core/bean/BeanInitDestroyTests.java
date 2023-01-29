package example.spring.core.bean;

import example.spring.core.bean.life.BeanInitDestroyDemo;
import example.spring.core.bean.life.BeanLazyInitDemo;
import example.spring.core.bean.life.UserFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * {@link Bean} 初始化和销毁的三种方式示例
 *
 * <ul>
 *     <li>{@link Bean} 的 initMethod 和 destroyMethod</li>
 *     <li>{@link InitializingBean} 和 {@link DisposableBean}</li>
 *     <li>{@link PostConstruct} 和 {@link PreDestroy}</li>
 * </ul>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-06
 */
@DisplayName("Spring Bean 初始化/销毁测试")
@ExtendWith(value = { OutputCaptureExtension.class })
public class BeanInitDestroyTests {

    @Test
    @DisplayName("Spring Bean 初始化/销毁三种普通方式测试")
    public void test(CapturedOutput capturedOutput) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanInitDestroyDemo.class);
        System.out.println("BeanInitDestroyDemo 示例结束");
        context.close();

        List<String> lines = Arrays.asList("[Pojo 构造方法]",
            "[Pojo 初始化方法]",
            "[Pojo2 构造方法]",
            "[Pojo2 初始化方法]",
            "[Pojo3 构造方法]",
            "[Pojo3 初始化方法]",
            "BeanInitDestroyDemo 示例结束",
            "[Pojo3 销毁方法]",
            "[Pojo2 销毁方法]",
            "[Pojo 销毁方法]");
        Assertions.assertThat(capturedOutput.getOut()).contains(lines);
    }

    @Test
    @DisplayName("Spring Bean 懒加载初始化测试")
    public void testLazyInit(CapturedOutput capturedOutput) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类）
        applicationContext.register(BeanLazyInitDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 非延迟初始化在 Spring 应用上下文启动完成后，被初始化
        System.out.println("Spring 应用上下文已启动...");
        // 依赖查找 UserFactory
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);
        System.out.println("Spring 应用上下文准备关闭...");
        // 关闭 Spring 应用上下文
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭...");

        List<String> lines = Arrays.asList("@PostConstruct : UserFactory 初始化中...",
            "InitializingBean#afterPropertiesSet() : UserFactory 初始化中...",
            "自定义初始化方法 initUserFactory() : UserFactory 初始化中...",
            "Spring 应用上下文已启动...",
            "Spring 应用上下文准备关闭...",
            "@PreDestroy : UserFactory 销毁中...",
            "DisposableBean#destroy() : UserFactory 销毁中...",
            "自定义销毁方法 doDestroy() : UserFactory 销毁中...",
            "Spring 应用上下文已关闭...");
        Assertions.assertThat(capturedOutput.getOut()).contains(lines);
    }

}
