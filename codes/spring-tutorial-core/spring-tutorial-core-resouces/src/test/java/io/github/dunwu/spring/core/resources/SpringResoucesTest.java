package io.github.dunwu.spring.core.resources;

import org.junit.Assert;
import org.junit.Test;
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
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-beans.xml");
		Person person = ctx.getBean("person_zhangsan", Person.class);
		Assert.assertNotNull(person);
		System.out.println(person);
		((ClassPathXmlApplicationContext) ctx).close();
	}

	@Test
	public void testClassPathXmlApplicationContext2() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "spring/spring-beans.xml", "spring/spring-beans2.xml" });
		Person zhangsan = ctx.getBean("person_zhangsan", Person.class);
		Assert.assertNotNull(zhangsan);
		System.out.println(zhangsan);
		Person lisi = ctx.getBean("person_lisi", Person.class);
		Assert.assertNotNull(lisi);
		System.out.println(lisi);
		((ClassPathXmlApplicationContext) ctx).close();
	}

	@Test
	public void testClassPathXmlApplicationContext3() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/*.xml");
		Person zhangsan = ctx.getBean("person_zhangsan", Person.class);
		Assert.assertNotNull(zhangsan);
		System.out.println(zhangsan);
		Person lisi = ctx.getBean("person_lisi", Person.class);
		Assert.assertNotNull(lisi);
		System.out.println(lisi);
		((ClassPathXmlApplicationContext) ctx).close();
	}

	@Test
	public void testFileSystemXmlApplicationContext() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:spring/spring-beans.xml");
		Person person = ctx.getBean("person_zhangsan", Person.class);
		Assert.assertNotNull(person);
		System.out.println(person);
		((FileSystemXmlApplicationContext) ctx).close();
	}

	@Test
	public void testFileSystemXmlApplicationContext2() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath*:*/*.xml");
		Person person = ctx.getBean("person_zhangsan", Person.class);
		Assert.assertNotNull(person);
		System.out.println(person);
		((FileSystemXmlApplicationContext) ctx).close();
	}

	@Test
	public void testGetResource() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/*.xml");
		Resource resource = ctx.getResource("spring/spring-beans.xml");
		Assert.assertNotNull(resource);
		((ClassPathXmlApplicationContext) ctx).close();
	}

}
