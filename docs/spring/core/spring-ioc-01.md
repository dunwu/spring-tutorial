---
title: Spring IoC（一）
date: 2017/11/08
categories:
- spring
tags:
- spring
- core
- ioc
---

## IoC 概念

### IoC是什么

> **IoC，是 Inversion of Control 的缩写，即控制反转。**
>
> - 上层模块不应该依赖于下层模块，它们共同依赖于一个抽象
> - 抽象不能依赖于具体实现，具体实现依赖于抽象 
>
> *注：又称为依赖倒置原则。这是设计模式六大原则之一。*

IoC 不是什么技术，而是一种设计思想。在 Java 开发中，IoC 意味着将你设计好的对象交给容器控制，而不是传统的在你的对象内部直接控制。如何理解 Ioc 呢？理解 Ioc 的关键是要明确“谁控制谁，控制什么，为何是反转（有反转就应该有正转了），哪些方面反转了”，那我们来深入分析一下：

- **谁控制谁，控制什么：**传统 JavaSE 程序设计，我们直接在对象内部通过 new 进行创建对象，是程序主动去创建依赖对象；而 IoC 是有专门一个容器来创建这些对象，即由 IoC 容器来控制对象的创建；谁控制谁？当然是 IoC 容器控制了对象；控制什么？那就是主要控制了外部资源获取（不只是对象包括比如文件等）。
- **为何是反转，哪些方面反转了：**有反转就有正转，传统应用程序是由我们自己在对象中主动控制去直接获取依赖对象，也就是正转；而反转则是由容器来帮忙创建及注入依赖对象；为何是反转？因为由容器帮我们查找及注入依赖对象，对象只是被动的接受依赖对象，所以是反转；哪些方面反转了？依赖对象的获取被反转了。

用图例说明一下，传统程序设计如图2-1，都是主动去创建相关对象然后再组合起来：

![img](http://sishuok.com/forum/upload/2012/2/19/a02c1e3154ef4be3f15fb91275a26494__1.JPG) 

图2-1 传统应用程序示意图

当有了 IoC/DI 的容器后，在客户端类中不再主动去创建这些对象了，如图2-2所示:

 ![img](http://sishuok.com/forum/upload/2012/2/19/6fdf1048726cc2edcac4fca685f050ac__2.JPG)

图2-2有IoC/DI容器后程序结构示意图

### IoC能做什么

IoC 不是一种技术，只是一种思想，一个重要的面向对象编程的法则，它能指导我们如何设计出松耦合、更优良的程序。传统应用程序都是由我们在类内部主动创建依赖对象，从而导致类与类之间高耦合，难于测试；有了IoC容器后，把创建和查找依赖对象的控制权交给了容器，由容器进行注入组合对象，所以对象与对象之间是松散耦合，这样也方便测试，利于功能复用，更重要的是使得程序的整个体系结构变得非常灵活。

其实 IoC 对编程带来的最大改变不是从代码上，而是从思想上，发生了“主从换位”的变化。应用程序原本是老大，要获取什么资源都是主动出击，但是在 IoC/DI 思想中，应用程序就变成被动的了，被动的等待 IoC 容器来创建并注入它所需要的资源了。

IoC 很好的体现了面向对象设计法则之一—— 好莱坞法则：“别找我们，我们找你”；即由 IoC 容器帮对象找相应的依赖对象并注入，而不是由对象主动去找。

### 依赖注入

> DI，是 Dependency Injection 的缩写，即依赖注入。
>
> 依赖注入是 IoC 的最常见形式。
>
> 容器全权负责的组件的装配，它会把符合依赖关系的对象通过 JavaBean属性或者构造函数传递给需要的对象。

DI 是组件之间依赖关系由容器在运行期决定，形象的说，即由容器动态的将某个依赖关系注入到组件之中。依赖注入的目的并非为软件系统带来更多功能，而是为了提升组件重用的频率，并为系统搭建一个灵活、可扩展的平台。通过依赖注入机制，我们只需要通过简单的配置，而无需任何代码就可指定目标需要的资源，完成自身的业务逻辑，而不需要关心具体的资源来自何处，由谁实现。

理解 DI 的关键是：“谁依赖谁，为什么需要依赖，谁注入谁，注入了什么”，那我们来深入分析一下：

- **谁依赖于谁：**当然是应用程序依赖于 IoC 容器；
- **为什么需要依赖：**应用程序需要 IoC 容器来提供对象需要的外部资源；
- **谁注入谁：**很明显是 IoC 容器注入应用程序某个对象，应用程序依赖的对象；
- **注入了什么**：就是注入某个对象所需要的外部资源（包括对象、资源、常量数据）。

### IoC 和 DI

其实它们是同一个概念的不同角度描述，由于控制反转概念比较含糊（可能只是理解为容器控制对象这一个层面，很难让人想到谁来维护对象关系），所以2004年大师级人物Martin Fowler又给出了一个新的名字：“依赖注入”，相对IoC 而言，“依赖注入”明确描述了“被注入对象依赖 IoC 容器配置依赖对象”。

> 注：如果想要更加深入的了解 IoC 和 DI，请参考大师级人物 Martin Fowler 的一篇经典文章 [Inversion of Control Containers and the Dependency Injection pattern](http://www.martinfowler.com/articles/injection.html) 。

### IoC容器

IoC 容器就是具有依赖注入功能的容器。IoC 容器负责实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。应用程序无需直接在代码中 new 相关的对象，应用程序由 IoC 容器进行组装。在 Spring 中 BeanFactory 是IoC容器的实际代表者。

Spring IoC 容器如何知道哪些是它管理的对象呢？这就需要配置文件，Spring IoC 容器通过读取配置文件中的配置元数据，通过元数据对应用中的各个对象进行实例化及装配。一般使用基于 xml 配置文件进行配置元数据，而且 Spring 与配置文件完全解耦的，可以使用其他任何可能的方式进行配置元数据，比如注解、基于 java 文件的、基于属性文件的配置都可以

那Spring IoC 容器管理的对象叫什么呢？

### Bean

> **JavaBean** 是一种 JAVA 语言写成的可重用组件。为写成 JavaBean，类必须是具体的和公共的，并且具有无参数的构造器。JavaBean 对外部通过提供 getter / setter 方法来访问其成员。

由 IoC 容器管理的那些组成你应用程序的对象我们就叫它 Bean。Bean 就是由 Spring 容器初始化、装配及管理的对象，除此之外，bean 就与应用程序中的其他对象没有什么区别了。那 IoC 怎样确定如何实例化 Bean、管理 Bean 之间的依赖关系以及管理 Bean 呢？这就需要配置元数据，在 Spring 中由 BeanDefinition 代表，后边会详细介绍，配置元数据指定如何实例化 Bean、如何组装 Bean 等。

## IoC 容器

### 核心接口

`org.springframework.beans` 和 `org.springframework.context` 是 IoC 容器的基础。

在 Spring 中，有两种 IoC 容器：`BeanFactory` 和 `ApplicationContext`。

- `BeanFactory`：Spring 实例化、配置和管理对象的最基本接口。
- `ApplicationContext`：BeanFactory 的子接口。它还扩展了其他一些接口，以支持更丰富的功能，如：国际化、访问资源、事件机制、更方便的支持 AOP、在web应用中指定应用层上下文等。  

实际开发中，更推荐使用 `ApplicationContext` 作为 IoC 容器，因为它的功能远多于 `FactoryBean`。 

常见 `ApplicationContext` 实现：

- **ClassPathXmlApplicationContext**：`ApplicationContext` 的实现，从 classpath 获取配置文件；


```java
BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath.xml");
```

- **FileSystemXmlApplicationContext**：`ApplicationContext` 的实现，从文件系统获取配置文件。

```java
BeanFactory beanFactory = new FileSystemXmlApplicationContext("fileSystemConfig.xml");
```

### IoC 容器工作步骤

使用 IoC 容器可分为三步骤： 

1. 配置元数据：需要配置一些元数据来告诉Spring，你希望容器如何工作，具体来说，就是如何去初始化、配置、管理 JavaBean 对象。


2. 实例化容器：由 IoC容器解析配置的元数据。IoC 容器的 Bean Reader 读取并解析配置文件，根据定义生成 BeanDefinition 配置元数据对象，IoC 容器根据 BeanDefinition 进行实例化、配置及组装 Bean。


3. 使用容器：由客户端实例化容器，获取需要的 Bean。


#### 配置元数据 ####  
> **元数据（Metadata）**
> 又称中介数据、中继数据，为描述数据的数据（data about data），主要是描述数据属性（property）的信息。  

配置元数据的方式：

- **基于 xml 配置**：Spring 的传统配置方式。在 `<beans>` 标签中配置元数据内容。 

  缺点是当 JavaBean 过多时，产生的配置文件足以让你眼花缭乱。 

- **基于注解配置**：Spring2.5 引入。可以大大简化你的配置。 

- **基于 Java 配置**：可以使用 Java 类来定义 JavaBean 。

  为了使用这个新特性，需要用到 `@Configuration` 、`@Bean` 、`@Import` 和 `@DependsOn` 注解。

### Bean概述 ###
一个Spring容器管理一个或多个bean。 
这些bean根据你配置的元数据（比如xml形式）来创建。 
Spring IoC容器本身，并不能识别你配置的元数据。为此，要将这些配置信息转为Spring能识别的格式——BeanDefinition对象。  

#### 命名 Bean ####
指定id和name属性不是必须的。 
Spring中，并非一定要指定id和name属性。实际上，Spring会自动为其分配一个特殊名。 
如果你需要引用声明的bean，这时你才需要一个标识。官方推荐驼峰命名法来命名。  

#### 支持别名 ####
可能存在这样的场景，不同系统中对于同一bean的命名方式不一样。 
为了适配，Spring 支持 `<alias>` 为bean添加别名的功能。  
```xml
<alias name="subsystemA-dataSource" alias="subsystemB-dataSource"/>
<alias name="subsystemA-dataSource" alias="myApp-dataSource" />
```

#### 实例化Bean ####
**构造器方式**  
```xml
<bean id="exampleBean" class="examples.ExampleBean"/>
```

**静态工厂方法**


### 依赖 ###
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


