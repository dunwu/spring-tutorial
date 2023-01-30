package example.spring.core.bean;

import example.spring.core.bean.life.BeanLifeCycleDemo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@DisplayName("Bean 生命周期测试")
@ExtendWith(value = { SpringExtension.class, OutputCaptureExtension.class })
@ContextConfiguration("/META-INF/bean/BeanLifeCycle.xml")
public class BeanLifeTests {

    @Test
    @DisplayName("Bean 生命周期测试（xml 方式）")
    public void testBeanLifeByXml(CapturedOutput capturedOutput) {
        // XML 配置方式
        ClassPathXmlApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("/META-INF/bean/BeanLifeCycle.xml");
        // 关闭应用上下文容器
        applicationContext.close();

        List<String> lines = Arrays.asList("setBeanName -> beanLifecycle",
            "setBeanFactory -> beanFactory",
            "setApplicationContext -> applicationContext",
            "afterPropertiesSet",
            "<bean> init-method",
            "destroy",
            "<bean> 的 destroy-method");
        Assertions.assertThat(capturedOutput.getOut()).contains(lines);
    }

    @Test
    @DisplayName("Bean 生命周期测试（注解方式）")
    public void testBeanLifeByAnnotation(CapturedOutput capturedOutput) {
        // 注解配置方式
        AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(BeanLifeCycleDemo.class);
        System.out.println("BeanLifeCycleDemo 示例结束");
        // 关闭应用上下文容器
        applicationContext.close();

        List<String> lines = Arrays.asList("setBeanName -> beanLifecycle",
            "setBeanFactory -> beanFactory",
            "setApplicationContext -> applicationContext",
            "afterPropertiesSet",
            "<bean> init-method",
            "postProcessBeforeInitialization -> io.github.dunwu.spring.core.bean.BeanLifeTests.ORIGINAL",
            "postProcessAfterInitialization -> io.github.dunwu.spring.core.bean.BeanLifeTests.ORIGINAL",
            "setBeanFactory -> beanFactory",
            "setApplicationContext -> applicationContext",
            "afterPropertiesSet",
            "<bean> init-method",
            "BeanLifeCycleDemo 示例结束",
            "destroy",
            "<bean> 的 destroy-method");
        Assertions.assertThat(capturedOutput.getOut()).contains(lines);
    }

}
