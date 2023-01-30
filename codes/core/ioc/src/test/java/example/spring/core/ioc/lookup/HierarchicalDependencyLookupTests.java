package example.spring.core.ioc.lookup;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 层次性依赖查找测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-06
 */
public class HierarchicalDependencyLookupTests {

    @Test
    @DisplayName("层次性依赖查找")
    public void test() {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 ObjectProviderDemo 作为配置类（Configuration Class）
        applicationContext.register(ObjectProviderDemo.class);

        // 1. 获取 HierarchicalBeanFactory <- ConfigurableBeanFactory <- ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        Assertions.assertThat(beanFactory.getParentBeanFactory()).isNull();
        System.out.println("当前 BeanFactory 的 Parent BeanFactory ： " + beanFactory.getParentBeanFactory());

        // 2. 设置 Parent BeanFactory
        HierarchicalBeanFactory parentBeanFactory = HierarchicalDependencyLookupDemo.createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        Assertions.assertThat(beanFactory.getParentBeanFactory()).isNotNull();
        System.out.println("当前 BeanFactory 的 Parent BeanFactory ： " + beanFactory.getParentBeanFactory());

        Assertions.assertThat(beanFactory.containsLocalBean("user")).isFalse();
        Assertions.assertThat(parentBeanFactory.containsLocalBean("user")).isTrue();
        HierarchicalDependencyLookupDemo.displayContainsLocalBean(beanFactory, "user");
        HierarchicalDependencyLookupDemo.displayContainsLocalBean(parentBeanFactory, "user");

        Assertions.assertThat(HierarchicalDependencyLookupDemo.containsBean(beanFactory, "user")).isTrue();
        Assertions.assertThat(HierarchicalDependencyLookupDemo.containsBean(parentBeanFactory, "user")).isTrue();
        HierarchicalDependencyLookupDemo.displayContainsBean(beanFactory, "user");
        HierarchicalDependencyLookupDemo.displayContainsBean(parentBeanFactory, "user");

        // 启动应用上下文
        applicationContext.refresh();

        // 关闭应用上下文
        applicationContext.close();
    }

}
