# IoC容器

## 核心接口

`org.springframework.beans` 和 `org.springframework.context` 是 IoC 容器的基础。

在 Spring 中，有两种 IoC 容器：`BeanFactory` 和 `ApplicationContext`。

- `BeanFactory`：Spring 实例化、配置和管理对象的最基本接口。
- `ApplicationContext`：BeanFactory 的子接口。它还扩展了其他一些接口，以支持更丰富的功能，如：国际化、访问资源、事件机制、更方便的支持 AOP、在web应用中指定应用层上下文等。  

实际开发中，更推荐使用 `ApplicationContext` 作为 IoC 容器，因为它的功能远多于 `FactoryBean`。 

#### 常见 ApplicationContext 实现

- **ClassPathXmlApplicationContext**：`ApplicationContext` 的实现，从 classpath 获取配置文件；


```java
BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath.xml");
```

- **FileSystemXmlApplicationContext**：`ApplicationContext` 的实现，从文件系统获取配置文件。

```java
BeanFactory beanFactory = new FileSystemXmlApplicationContext("fileSystemConfig.xml");
```

## IoC 容器工作步骤

使用 IoC 容器可分为三步骤： 

1. 配置元数据：需要配置一些元数据来告诉Spring，你希望容器如何工作，具体来说，就是如何去初始化、配置、管理 JavaBean 对象。


2. 实例化容器：由 IoC容器解析配置的元数据。IoC 容器的 Bean Reader 读取并解析配置文件，根据定义生成 BeanDefinition 配置元数据对象，IoC 容器根据 BeanDefinition 进行实例化、配置及组装 Bean。


3. 使用容器：由客户端实例化容器，获取需要的 Bean。

整个过程是不是很简单，执行过程如图2-5，其实IoC容器很容易使用，主要是如何进行Bean定义。

 ![img](http://sishuok.com/forum/upload/2012/2/19/2e57867020e686671fde7923f891ab69__%E6%9C%AA%E6%A0%87%E9%A2%98-5.jpg)

图2-5 Spring Ioc容器


### 配置元数据 ###  
> **元数据（Metadata）**
> 又称中介数据、中继数据，为描述数据的数据（data about data），主要是描述数据属性（property）的信息。  

配置元数据的方式：

- **基于 xml 配置**：Spring 的传统配置方式。在 `<beans>` 标签中配置元数据内容。 

  缺点是当 JavaBean 过多时，产生的配置文件足以让你眼花缭乱。 

- **基于注解配置**：Spring2.5 引入。可以大大简化你的配置。 

- **基于 Java 配置**：可以使用 Java 类来定义 JavaBean 。

  为了使用这个新特性，需要用到 `@Configuration` 、`@Bean` 、`@Import` 和 `@DependsOn` 注解。

Xml配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd">
  <import resource="resource1.xml" />
  <bean id="bean1" class=""></bean>
  <bean id="bean2" class=""></bean>
  <bean name="bean2" class=""></bean>

  <alias alias="bean3" name="bean2"/>
  <import resource="resource2.xml" />  
</beans>
```
标签说明：

- `<beans>` 是 Spring 配置文件的根节点。
- `<bean>` 用来定义一个 JavaBean。`id` 属性是它的标识，在文件中必须唯一；`class` 属性是它关联的类。  
- `<alias>` 用来定义 Bean 的别名。
- `<import>` 用来导入其他配置文件的 Bean 定义。这是为了加载多个配置文件，当然也可以把这些配置文件构造为一个数组（new String[] {“config1.xml”, config2.xml}）传给 `ApplicationContext` 实现类进行加载多个配置文件，那一个更适合由用户决定；这两种方式都是通过调用 Bean Definition Reader 读取 Bean 定义，内部实现没有任何区别。`<import>` 标签可以放在 `<beans>` 下的任何位置，没有顺序关系。

### 实例化容器 ###
实例化容器的过程： 
定位资源（XML配置文件） 
读取配置信息(Resource) 
转化为Spring 可识别的数据形式（BeanDefinition）  
```java
ApplicationContext context =  
      new ClassPathXmlApplicationContext(new String[] {"services.xml", "daos.xml"});
```
组合 xml 配置文件 
配置的 Bean 功能各不相同，都放在一个xml文件中，不便管理。 
Java 设计模式讲究职责单一原则。配置其实也是如此，功能不同的JavaBean应该被组织在不同的xml文件中。然后使用import标签把它们统一导入。
```xml
<import resource="classpath:spring/applicationContext.xml"/>
<import resource="/WEB-INF/spring/service.xml"/>
```

### 使用容器 ###
使用容器的方式就是通过`getBean`获取IoC容器中的JavaBean。 
Spring也有其他方法去获得JavaBean，但是Spring并不推荐其他方式。 
```java
// create and configure beans
ApplicationContext context =
new ClassPathXmlApplicationContext(new String[] {"services.xml", "daos.xml"});
// retrieve configured instance
PetStoreService service = context.getBean("petStore", PetStoreService.class);
// use configured instance
List<String> userList = service.getUsernameList();
```

## Bean概述 ##
一个Spring容器管理一个或多个bean。 
这些bean根据你配置的元数据（比如xml形式）来创建。 
Spring IoC容器本身，并不能识别你配置的元数据。为此，要将这些配置信息转为Spring能识别的格式——BeanDefinition对象。  

### 命名 Bean ###
指定id和name属性不是必须的。 
Spring中，并非一定要指定id和name属性。实际上，Spring会自动为其分配一个特殊名。 
如果你需要引用声明的bean，这时你才需要一个标识。官方推荐驼峰命名法来命名。  

### 支持别名 ###
可能存在这样的场景，不同系统中对于同一bean的命名方式不一样。 
为了适配，Spring 支持 `<alias>` 为bean添加别名的功能。  
```xml
<alias name="subsystemA-dataSource" alias="subsystemB-dataSource"/>
<alias name="subsystemA-dataSource" alias="myApp-dataSource" />
```

### 实例化Bean ###
**构造器方式**  
```xml
<bean id="exampleBean" class="examples.ExampleBean"/>
```

**静态工厂方法**


## 依赖 ##
依赖注入 
依赖注入有两种主要方式：  
- 构造器注入  
- Setter注入 
  构造器注入有可能出现循环注入的错误。如：
```java
class A {
	public A(B b){}
}
class B {
	public B(A a){}
}
```

**依赖和配置细节**
使用 depends-on 
Lazy-initialized Bean 
自动装配 
方法注入 


