# Spring Bean

<!-- TOC depthFrom:2 depthTo:3 -->

<!-- /TOC -->

## 定义 Spring Bean

如何注册一个 Spring Bean？

通过BeanDefinition 和外部单体对象来注册

### 命名 Spring Bean

指定 id 和 name 属性不是必须的。Spring 中，并非一定要指定 id 和 name 属性。实际上，Spring 会自动为其分配一个特殊名。如果你需要引用声明的 bean，这时你才需要一个标识。官方推荐驼峰命名法来命名。

### Spring Bean 别名

可能存在这样的场景，不同系统中对于同一 bean 的命名方式不一样。
为了适配，Spring 支持 `<alias>` 为 bean 添加别名的功能。

```xml
<bean id="user" class="io.github.dunwu.spring.core.domain.User"
  p:name="张三" p:age="18">
</bean>
<alias name="sysUser" alias="superUser" />
```

## BeanDefinition

### 什么是 BeanDefinition

`BeanDefinition` 是 Spring Framework 中定义 Bean 的配置元信息接口，包含：

- Bean 类名
- Bean 行为配置元素，如：作用域、自动绑定的模式、生命周期回调等
- 其他 Bean 引用
- 配置设置，如 Bean 属性（Properties）

### BeanDefinition 构建

通过 `BeanDefinitionBuilder`

通过 `AbstractBeanDefinition` 以及派生类

```java
		// 通过 BeanDefinitionBuilder 构建 BeanDefinition
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
		builder.addPropertyValue("name", "Tom");
		builder.addPropertyValue("age", 20);
		BeanDefinition beanDefinition = builder.getBeanDefinition();
		System.out.println(beanDefinition.toString());
		// BeanDefinition 并非 Bean 状态，可以自定义修改

		// 通过 AbstractBeanDefinition 及派生类构建 BeanDefinition
		GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
		// 设置 Bean 类型
		genericBeanDefinition.setBeanClass(User.class);
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.addPropertyValue("name", "Tom");
		propertyValues.addPropertyValue("age", 20);
		genericBeanDefinition.setPropertyValues(propertyValues);
```

## 注册 Spring Bean

注册 Spring Bean 实际上是将 BeanDefinition 注册到 IoC 容器中。

（一）XML 配置元信息

Spring 的传统配置方式。在 `<bean>` 标签中配置元数据内容。

缺点是当 JavaBean 过多时，产生的配置文件足以让你眼花缭乱。

（二）注解配置元信息

使用 `@Bean`、`@Component`、`@Import` 注解注册 Spring Bean。

（三）Java API 配置元信息

使用 `BeanDefinitionRegistry#registerBeanDefinition(String beanName, BeanDefinition beanDefinition)` 以指定 Bean 名称的方式注册 Spring Bean。

【示例】

```java
@Import(AnnotationComponentScan.MyConfiguration.class)
public class AnnotationComponentScan {

   public static void main(String[] args) {
      // 创建 BeanFactory 容器
      AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

      // 1.注册配置类
      ctx.register(AnnotationComponentScan.class);

      // 2.通过 Java API 注册
      registerBeanDefinition(ctx, "zhaoliu", User.class);

      // 启动应用上下文
      ctx.refresh();

      User wangwu = (User) ctx.getBean("wangwu");
      System.out.println("wangwu info: " + wangwu);
      System.out.println("All beans of User: " + ctx.getBeansOfType(User.class));

      //显示关闭 ApplicationContext
      ctx.close();
   }

   public static void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass) {
      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
      builder.addPropertyValue("name", "赵六");
      builder.addPropertyValue("age", 31);

      // 注册 BeanDefinition
      registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
   }

   @Configuration
   public static class MyConfiguration {

      // 注解方式配置元信息
      @Bean(name = { "user", "wangwu" })
      public User user() {
         return new User("王五", 21);
      }

   }

}
```

## 实例化 Spring Bean

Spring Bean 实例化方式：

- 常规方式
  - 通过构造器（配置元信息：XML、Java 注解和 Java API）
  - 通过静态工厂方法（配置元信息：XML、Java 注解和 Java API）
  - 通过 Bean 工厂方法（配置元信息：XML、Java 注解和 Java API）
  - 通过 FactoryBean（配置元信息：XML、Java 注解和 Java API）
- 特殊方式
  - 通过 ServiceLoaderFactoryBean（配置元信息：XML、Java 注解和 Java API ）
  - 通过 AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean)
  - 通过 BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)

## 初始化 Spring Bean

- `@PostConstruct` 标注方法
- 实现 `InitializingBean` 接口的 `afterPropertiesSet()` 方法
- 自定义初始化方法
  - XML 配置：`<bean init-method=”init” ... />`
  - Java 注解：`@Bean(initMethod=”init”)`
  - Java API：`AbstractBeanDefinition#setInitMethodName(String)`

## 销毁 Spring Bean

Spring Bean 垃圾回收步骤：

- 关闭Spring 容器（应用上下文）
- 执行GC
- Spring Bean 覆盖的finalize() 方法被回调

## 参考资料

- [Spring 官方文档之 Core Technologies](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans)
