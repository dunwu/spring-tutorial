# Spring Bean 生命周期

<!-- TOC depthFrom:2 depthTo:3 -->

- [定义 Spring Bean](#定义-spring-bean)
- [BeanDefinition](#beandefinition)
  - [什么是 BeanDefinition](#什么是-beandefinition)
  - [BeanDefinition 元信息](#beandefinition-元信息)
  - [BeanDefinition 构建](#beandefinition-构建)
- [命名 Spring Bean](#命名-spring-bean)
  - [Spring Bean 命名规则](#spring-bean-命名规则)
  - [Spring Bean 命名生成器](#spring-bean-命名生成器)
  - [Spring Bean 别名](#spring-bean-别名)
- [注册 Spring Bean](#注册-spring-bean)
- [实例化 Spring Bean](#实例化-spring-bean)
- [初始化 Spring Bean](#初始化-spring-bean)
- [销毁 Spring Bean](#销毁-spring-bean)
- [Spring Bean 垃圾回收](#spring-bean-垃圾回收)
- [参考资料](#参考资料)

<!-- /TOC -->

## 定义 Spring Bean

如何注册一个 Spring Bean？

通过 BeanDefinition 和外部单体对象来注册

## Spring Bean 基本用法

### BeanDefinition

#### 什么是 BeanDefinition

`BeanDefinition` 是 Spring Framework 中定义 Bean 的配置元信息接口，包含：

- Bean 类名
- Bean 行为配置元素，如：作用域、自动绑定的模式、生命周期回调等
- 其他 Bean 引用
- 配置设置，如 Bean 属性（Properties）

#### BeanDefinition 元信息

`BeanDefinition` 元信息如下：

| 属性（Property）              | 说明                                     |
| ----------------------------- | ---------------------------------------- |
| Class Bean                    | 全类名，必须是具体类，不能用抽象类或接口 |
| Name Bean                     | 的名称或者 ID                            |
| Scope Bean                    | 的作用域（如：singleton、prototype 等）  |
| Constructor arguments Bean    | 构造器参数（用于依赖注入）               |
| Properties Bean               | 属性设置（用于依赖注入）                 |
| Autowiring mode Bean          | 自动绑定模式（如：通过名称 byName）      |
| Lazy initialization mode Bean | 延迟初始化模式（延迟和非延迟）           |
| Initialization method Bean    | 初始化回调方法名称                       |
| Destruction method Bean       | 销毁回调方法名称                         |

#### BeanDefinition 构建

BeanDefinition 构建方式：

- 通过 `BeanDefinitionBuilder`

- 通过 `AbstractBeanDefinition` 以及派生类

```java
// 1.通过 BeanDefinitionBuilder 构建
BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
// 通过属性设置
beanDefinitionBuilder
  .addPropertyValue("id", 1)
  .addPropertyValue("name", "小马哥");
// 获取 BeanDefinition 实例
BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
// BeanDefinition 并非 Bean 终态，可以自定义修改

// 2. 通过 AbstractBeanDefinition 以及派生类
GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
// 设置 Bean 类型
genericBeanDefinition.setBeanClass(User.class);
// 通过 MutablePropertyValues 批量操作属性
MutablePropertyValues propertyValues = new MutablePropertyValues();
//        propertyValues.addPropertyValue("id", 1);
//        propertyValues.addPropertyValue("name", "小马哥");
propertyValues
  .add("id", 1)
  .add("name", "小马哥");
// 通过 set MutablePropertyValues 批量操作属性
genericBeanDefinition.setPropertyValues(propertyValues);
```

### 命名 Spring Bean

#### Spring Bean 命名规则

每个 Bean 拥有一个或多个标识符（identifiers），这些标识符在 Bean 所在的容器必须是唯一的。通常，一个 Bean 仅有一个标识符，如果需要额外的，可考虑使用别名（Alias）来扩充。

在基于 XML 的配置元信息中，开发人员可用 id 或者 name 属性来规定 Bean 的标识符。通常 Bean 的标识符由字母组成，允许出现特殊字符。如果要想引入 Bean 的别名的话，可在 name 属性使用半角逗号（“,”）或分号（“;”) 来间隔。

Bean 的 id 或 name 属性并非必须制定，如果留空的话，容器会为 Bean 自动生成一个唯一的名称。Bean 的命名尽管没有限制，不过官方建议采用驼峰的方式，更符合 Java 的命名约定。

#### Spring Bean 命名生成器

Spring 提供了两种 Spring Bean 命名生成器：

- DefaultBeanNameGenerator：默认通用 BeanNameGenerator 实现。
- AnnotationBeanNameGenerator：基于注解扫描的 BeanNameGenerator 实现。

```java
public interface BeanNameGenerator {
   String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}
```

#### Spring Bean 别名

可能存在这样的场景，不同系统中对于同一 bean 的命名方式不一样。
为了适配，Spring 支持 `<alias>` 为 bean 添加别名的功能。

```xml
<bean id="user" class="io.github.dunwu.spring.core.domain.User"
  p:name="张三" p:age="18">
</bean>
<alias name="sysUser" alias="superUser" />
```

Bean 别名（Alias）的作用：

- 复用现有的 BeanDefinition
- 更具有场景化的命名方法，比如：
  - `<alias name="myApp-dataSource" alias="subsystemA-dataSource"/>`
  - `<alias name="myApp-dataSource" alias="subsystemB-dataSource"/>`

### 注册 Spring Bean

注册 Spring Bean 实际上是将 BeanDefinition 注册到 IoC 容器中。

（一）XML 配置元信息

Spring 的传统配置方式。在 `<bean>` 标签中配置元数据内容。

缺点是当 JavaBean 过多时，产生的配置文件足以让你眼花缭乱。

（二）注解配置元信息

使用 `@Bean`、`@Component`、`@Import` 注解注册 Spring Bean。

（三）Java API 配置元信息

- 命名方式：`BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)`
- 非命名方式：`BeanDefinitionReaderUtils#registerWithGeneratedName(AbstractBeanDefinition,Be`
  `anDefinitionRegistry)`
- 配置类方式：`AnnotatedBeanDefinitionReader#register(Class...)`

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

### 实例化 Spring Bean

Spring Bean 实例化方式：

- 常规方式
  - 通过构造器（配置元信息：XML、Java 注解和 Java API）
  - 通过静态工厂方法（配置元信息：XML、Java 注解和 Java API）
  - 通过 Bean 工厂方法（配置元信息：XML、Java 注解和 Java API）
  - 通过 `FactoryBean`（配置元信息：XML、Java 注解和 Java API）
- 特殊方式
  - 通过 `ServiceLoaderFactoryBean`（配置元信息：XML、Java 注解和 Java API ）
  - 通过 `AutowireCapableBeanFactory#createBean(java.lang.Class, int, boolean)`
  - 通过 `BeanDefinitionRegistry#registerBeanDefinition(String,BeanDefinition)`

### 初始化 Spring Bean

初始化 Spring Bean 的方式有以下几种：

1. `@PostConstruct` 标注方法
2. 实现 `InitializingBean` 接口的 `afterPropertiesSet()` 方法
3. 自定义初始化方法

- XML 配置：`<bean init-method=”init” ... />`
- Java 注解：`@Bean(initMethod=”init”)`
- Java API：`AbstractBeanDefinition#setInitMethodName(String)`

注意：如果同时存在，执行顺序会按照序列执行。

### 销毁 Spring Bean

销毁 Spring Bean 的方式有以下几种：

1. `@PreDestroy` 标注方法
2. 实现 `DisposableBean` 接口的 `destroy()` 方法
3. 自定义销毁方法
   - XML 配置：`<bean destroy=”destroy” ... />`
   - Java 注解：`@Bean(destroy=”destroy”)`
   - Java API：`AbstractBeanDefinition#setDestroyMethodName(String)`

注意：如果同时存在，执行顺序会按照序列执行。

### Spring Bean 垃圾回收

Spring Bean 垃圾回收步骤：

1. 关闭 Spring 容器（应用上下文）
2. 执行 GC
3. Spring Bean 覆盖的 finalize() 方法被回调

## Spring Bean 生命周期

Spring Bean的生命周期是指：Bean从创建到销毁的过程，这个过程由 Spring 的 IOC 机制控制。

## 参考资料

- [Spring 官方文档之 Core Technologies](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans)
