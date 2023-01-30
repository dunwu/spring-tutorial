package example.spring.core.resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;

/**
 * 使用 ApplicationContext 构造器方法加载 Resouce 文件测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-04
 */
@SuppressWarnings("all")
public class SpringResoucesTest {

    @Test
    public void testClassPathXmlApplicationContext() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring-beans.xml");
        Person person = ctx.getBean("person_zhangsan", Person.class);
        Assertions.assertNotNull(person);
        System.out.println(person);
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    @Test
    public void testClassPathXmlApplicationContext2() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
            new String[] { "META-INF/spring-beans.xml", "META-INF/spring-beans2.xml" });
        Person zhangsan = ctx.getBean("person_zhangsan", Person.class);
        Assertions.assertNotNull(zhangsan);
        System.out.println(zhangsan);
        Person lisi = ctx.getBean("person_lisi", Person.class);
        Assertions.assertNotNull(lisi);
        System.out.println(lisi);
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    @Test
    public void testClassPathXmlApplicationContext3() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:META-INF/*.xml");
        Person zhangsan = ctx.getBean("person_zhangsan", Person.class);
        Assertions.assertNotNull(zhangsan);
        System.out.println(zhangsan);
        Person lisi = ctx.getBean("person_lisi", Person.class);
        Assertions.assertNotNull(lisi);
        System.out.println(lisi);
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    @Test
    public void testFileSystemXmlApplicationContext() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:META-INF/spring-beans.xml");
        Person person = ctx.getBean("person_zhangsan", Person.class);
        Assertions.assertNotNull(person);
        System.out.println(person);
        ((FileSystemXmlApplicationContext) ctx).close();
    }

    @Test
    public void testFileSystemXmlApplicationContext2() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath*:*/*.xml");
        Person zhangsan = ctx.getBean("person_zhangsan", Person.class);
        Assertions.assertNotNull(zhangsan);
        System.out.println(zhangsan);
        Person lisi = ctx.getBean("person_lisi", Person.class);
        Assertions.assertNotNull(lisi);
        System.out.println(lisi);
        ((FileSystemXmlApplicationContext) ctx).close();
    }

    @Test
    public void testGetResource() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/*.xml");
        Resource resource = ctx.getResource("META-INF/spring-beans.xml");
        Assertions.assertNotNull(resource);
        ((ClassPathXmlApplicationContext) ctx).close();
    }

}
