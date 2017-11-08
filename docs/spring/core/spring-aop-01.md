---
title: Spring AOP（一）
date: 2017/11/08
categories:
- spring
tags:
- spring
- core
- aop
---

## 概念

其实, 接触了这么久的 AOP, 我感觉, AOP 给人难以理解的一个关键点是它的概念比较多, 而且坑爹的是, 这些概念经过了中文翻译后, 变得面目全非, 相同的一个术语, 在不同的翻译下, 含义总有着各种莫名其妙的差别. 鉴于此, 我在本章的开头, 着重为为大家介绍一个 Spring AOP 的各项术语的基本含义. 为了术语传达的准确性, 我在接下来的叙述中, 能使用英文术语的地方, 尽量使用英文.

### 什么是 AOP

AOP(Aspect-Oriented Programming), 即 **面向切面编程**, 它与 OOP( Object-Oriented Programming, 面向对象编程) 相辅相成, 提供了与 OOP 不同的抽象软件结构的视角.
在 OOP 中, 我们以类(class)作为我们的基本单元, 而 AOP 中的基本单元是 **Aspect(切面)**

### 术语

#### Aspect(切面)

`aspect` 由 `pointcount` 和 `advice` 组成, 它既包含了横切逻辑的定义, 也包括了连接点的定义. Spring AOP就是负责实施切面的框架, 它将切面所定义的横切逻辑织入到切面所指定的连接点中.
AOP的工作重心在于如何将增强织入目标对象的连接点上, 这里包含两个工作:

1. 如何通过 pointcut 和 advice 定位到特定的 joinpoint 上
2. 如何在 advice 中编写切面代码.

**可以简单地认为, 使用 @Aspect 注解的类就是切面.**

#### advice(增强)

由 aspect 添加到特定的 join point(即满足 point cut 规则的 join point) 的一段代码.
许多 AOP 框架, 包括 Spring AOP, 会将 advice 模拟为一个拦截器(interceptor), 并且在 join point 上维护多个 advice, 进行层层拦截.
例如 HTTP 鉴权的实现, 我们可以为每个使用 RequestMapping 标注的方法织入 advice, 当 HTTP 请求到来时, 首先进入到 advice 代码中, 在这里我们可以分析这个 HTTP 请求是否有相应的权限, 如果有, 则执行 Controller, 如果没有, 则抛出异常. 这里的 advice 就扮演着鉴权拦截器的角色了.

#### 连接点(join point)

> a point during the execution of a program, such as the execution of a method or the handling of an exception. In Spring AOP, a join point always represents a method execution.

程序运行中的一些时间点, 例如一个方法的执行, 或者是一个异常的处理.
`在 Spring AOP 中, join point 总是方法的执行点, 即只有方法连接点.`

#### 切点(point cut)

匹配 join point 的谓词(a predicate that matches join points).
Advice 是和特定的 point cut 关联的, 并且在 point cut 相匹配的 join point 中执行. 
`在 Spring 中, 所有的方法都可以认为是 joinpoint, 但是我们并不希望在所有的方法上都添加 Advice, 而 pointcut 的作用就是提供一组规则(使用 AspectJ pointcut expression language 来描述) 来匹配joinpoint, 给满足规则的 joinpoint 添加 Advice.`

#### 关于join point 和 point cut 的区别

在 Spring AOP 中, 所有的方法执行都是 join point. 而 point cut 是一个描述信息, 它修饰的是 join point, 通过 point cut, 我们就可以确定哪些 join point 可以被织入 Advice. 因此 join point 和 point cut 本质上就是两个不同纬度上的东西.
`advice 是在 join point 上执行的, 而 point cut 规定了哪些 join point 可以执行哪些 advice`

#### introduction

为一个类型添加额外的方法或字段. Spring AOP 允许我们为 `目标对象` 引入新的接口(和对应的实现). 例如我们可以使用 introduction 来为一个 bean 实现 IsModified 接口, 并以此来简化 caching 的实现.

#### 目标对象(Target)

织入 advice 的目标对象. 目标对象也被称为 `advised object`.
`因为 Spring AOP 使用运行时代理的方式来实现 aspect, 因此 adviced object 总是一个代理对象(proxied object)`
`注意, adviced object 指的不是原来的类, 而是织入 advice 后所产生的代理类.`

#### AOP proxy

一个类被 AOP 织入 advice, 就会产生一个结果类, 它是融合了原类和增强逻辑的代理类.
在 Spring AOP 中, 一个 AOP 代理是一个 JDK 动态代理对象或 CGLIB 代理对象.

#### 织入(Weaving)

将 aspect 和其他对象连接起来, 并创建 adviced object 的过程.
根据不同的实现技术, AOP织入有三种方式:

- 编译器织入, 这要求有特殊的Java编译器.
- 类装载期织入, 这需要有特殊的类装载器.
- 动态代理织入, 在运行期为目标类添加增强(Advice)生成子类的方式.
  Spring 采用动态代理织入, 而AspectJ采用编译器织入和类装载期织入.

### advice 的类型

- before advice, 在 join point 前被执行的 advice. 虽然 before advice 是在 join point 前被执行, 但是它并不能够阻止 join point 的执行, 除非发生了异常(即我们在 before advice 代码中, 不能人为地决定是否继续执行 join point 中的代码)
- after return advice, 在一个 join point 正常返回后执行的 advice
- after throwing advice, 当一个 join point 抛出异常后执行的 advice
- after(final) advice, 无论一个 join point 是正常退出还是发生了异常, 都会被执行的 advice.
- around advice, 在 join point 前和 joint point 退出后都执行的 advice. 这个是最常用的 advice.

### 关于 AOP Proxy

Spring AOP 默认使用标准的 JDK 动态代理(dynamic proxy)技术来实现 AOP 代理, 通过它, 我们可以为任意的接口实现代理.
`如果需要为一个类实现代理, 那么可以使用 CGLIB 代理.` 当一个业务逻辑对象没有实现接口时, 那么Spring AOP 就默认使用 CGLIB 来作为 AOP 代理了. 即如果我们需要为一个方法织入 advice, 但是这个方法不是一个接口所提供的方法, 则此时 Spring AOP 会使用 CGLIB 来实现动态代理. 鉴于此, Spring AOP 建议基于接口编程, 对接口进行 AOP 而不是类.

## 彻底理解 aspect, join point, point cut, advice

看完了上面的理论部分知识, 我相信还是会有不少朋友感觉到 AOP 的概念还是很模糊, 对 AOP 中的各种概念理解的还不是很透彻. 其实这很正常, 因为 AOP 中的概念是在是太多了, 我当时也是花了老大劲才梳理清楚的.
下面我以一个简单的例子来比喻一下 AOP 中 aspect, jointpoint, pointcut 与 advice 之间的关系.

让我们来假设一下, 从前有一个叫爪哇的小县城, 在一个月黑风高的晚上, 这个县城中发生了命案. 作案的凶手十分狡猾, 现场没有留下什么有价值的线索. 不过万幸的是, 刚从隔壁回来的老王恰好在这时候无意中发现了凶手行凶的过程, 但是由于天色已晚, 加上凶手蒙着面, 老王并没有看清凶手的面目, 只知道凶手是个男性, 身高约七尺五寸. 爪哇县的县令根据老王的描述, 对守门的士兵下命令说: 凡是发现有身高七尺五寸的男性, 都要抓过来审问. 士兵当然不敢违背县令的命令, 只好把进出城的所有符合条件的人都抓了起来.

来让我们看一下上面的一个小故事和 AOP 到底有什么对应关系.
首先我们知道, 在 Spring AOP 中 join point 指代的是所有方法的执行点, 而 point cut 是一个描述信息, 它修饰的是 join point, 通过 point cut, 我们就可以确定哪些 join point 可以被织入 Advice. 对应到我们在上面举的例子, 我们可以做一个简单的类比, join point 就相当于 **爪哇的小县城里的百姓**, point cut 就相当于 **老王所做的指控, 即凶手是个男性, 身高约七尺五寸**, 而 advice 则是施加在符合老王所描述的嫌疑人的动作: **抓过来审问**.
为什么可以这样类比呢?

- join point --> 爪哇的小县城里的百姓: 因为根据定义, join point 是所有可能被织入 advice 的候选的点, 在 Spring AOP中, 则可以认为所有方法执行点都是 join point. 而在我们上面的例子中, 命案发生在小县城中, 按理说在此县城中的所有人都有可能是嫌疑人.
- point cut --> 男性, 身高约七尺五寸: 我们知道, 所有的方法(joint point) 都可以织入 advice, 但是我们并不希望在所有方法上都织入 advice, 而 pointcut 的作用就是提供一组规则来匹配joinpoint, 给满足规则的 joinpoint 添加 advice. 同理, 对于县令来说, 他再昏庸, 也知道不能把县城中的所有百姓都抓起来审问, 而是根据`凶手是个男性, 身高约七尺五寸`, 把符合条件的人抓起来. 在这里`凶手是个男性, 身高约七尺五寸` 就是一个修饰谓语, 它限定了凶手的范围, 满足此修饰规则的百姓都是嫌疑人, 都需要抓起来审问.
- advice --> 抓过来审问, advice 是一个动作, 即一段 Java 代码, 这段 Java 代码是作用于 point cut 所限定的那些 join point 上的. 同理, 对比到我们的例子中, `抓过来审问` 这个动作就是对作用于那些满足 `男性, 身高约七尺五寸` 的`爪哇的小县城里的百姓`.
- aspect: aspect 是 point cut 与 advice 的组合, 因此在这里我们就可以类比: **"根据老王的线索, 凡是发现有身高七尺五寸的男性, 都要抓过来审问"** 这一整个动作可以被认为是一个 aspect.

或则我们也可以从语法的角度来简单类比一下. 我们在学英语时, 经常会接触什么 `定语`, `被动句` 之类的概念, 那么可以做一个不严谨的类比, 即 `joinpoint` 可以认为是一个 `宾语`, 而 `pointcut` 则可以类比为修饰 `joinpoint` 的定语, 那么整个 `aspect` 就可以描述为: `满足 pointcut 规则的 joinpoint 会被添加相应的 advice 操作.`
