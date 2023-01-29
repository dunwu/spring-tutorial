package example.spring.core.bean.annotation.autowire;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用xml方式来实现依赖注入
 *
 * @author Zhang Peng
 * @since 2016年7月31日
 */
public class App {

    public static void main(String[] args) throws Exception {

        // 创建 BeanFactory 容器
        ClassPathXmlApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("META-INF/spring-autowire.xml");

        // 自动装配：byName
        Musician chopinbyName = (Musician) applicationContext.getBean("chopinbyName");
        chopinbyName.perform();

        // 自动装配：byType
        Musician chopinbyType = (Musician) applicationContext.getBean("chopinbyType");
        chopinbyType.perform();

        // 自动装配：constructor
        Poet libai = (Poet) applicationContext.getBean("libai");
        libai.perform();

        // 关闭应用上下文
        applicationContext.close();
    }

}
