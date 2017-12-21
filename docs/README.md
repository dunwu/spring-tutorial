# spring-notes

> Spring 学习笔记。
> 
> 以简单范例来展示 spring 在 web 开发中的各种应用。
>
> 扩展学习：更多 Java 技术栈知识（JavaSE/JavaEE/Java 库/Java 工具/Java 框架）可以在 [java-stack](https://github.com/dunwu/java-stack) 了解。
>

<p align="center">
  <a href="https://dunwu.gitbooks.io/spring-notes/" target="_blank">
    :point_right: spring-notes 文档在线阅读
  </a>
</p>

## 内容

### Core

- **spring-notes-core-ioc** spring 的依赖注入范例。
- **spring-notes-core-aop** spring 的切面编程范例。
- **spring-notes-core-validator** spring 的校验器范例。自定义一个简单的校验器。

### Data

- **spring-notes-data-db** spring 集成不同数据源的范例。

- **spring-notes-data-orm** spring 集成 orm 框架的范例。

  hibernate 和 mybatis 都是比较流行的 orm 框架。

  hibernate 功能更强大，但是也更复杂，学习周期更长，数据调优也更为复杂。

  相对于新手，mybatis 更容易上手，因此这里选用 mybatis 来展示如何集成。

### Web

- **spring-notes-web-mvc** 展示 spring mvc 的特性。
- **spring-notes-web-websocket** 展示 spring 如何支持 HTML5 重要新特性 websocket。

### Integration

- **spring-notes-integration-3party** spring  集成第三方库。
- **spring-notes-integration-cache** spring  集成 ehcache 实现缓存方案。

- **spring-notes-integration-rmi** spring 的远程调用范例。分为 server 端和 client 端。
- **spring-notes-integration-rpc** spring 集成 dubbo 实现远程服务化调用。
- **spring-notes-integration-scheduler** spring 的调度任务范例。

### Example

- **spring-notes-example-helloworld**：spring 第一个简单例子。

### Boot

- **spring-notes-boot**：spring-boot 是 spring 的快速构建框架。

### Security

- **spring-notes-sccurity-shiro**：spring 集成 shiro 实现安全框架的范例。

  spring 自身的安全框架 spring-security 相比 shiro 较为重型化，并且 shiro 可以满足大部分的应用，所以这里选用 shiro。

## 说明

### 环境说明

**重要库版本**

- **Jdk**：1.8

- **Spring**：4.1.4.RELEASE

- **Maven**：3.5.2

**嵌入式服务器版本**

- **Jetty**：Jetty 9

- **Tomcat**：Tomcat 7

### 项目结构

- **codes**：代码目录。
- **docs**：文档目录。

### codes 使用说明

为了便于展示示例，所有 war 包形式的项目都可以使用 maven 插件快速启动嵌入式服务器，支持 Tomcat 和 Jetty 两种方式。

Tomcat7 嵌入式服务器启动：

```bash
$ mvn tomcat7:run
```

Jetty 嵌入式服务器启动：

```bash
$ mvn jetty:run
```

如果子项目中无特殊说明，默认端口配置为 8089，当然，你可以自己指定。

启动成功后，访问 http://localhost:8089 。

### docs 使用说明

docs 部分已经使用 Gitbook 生成电子书：

:point_right: [**spring-notes 文档在线阅读**](https://dunwu.gitbooks.io/spring-notes/)

扩展学习：更多 Java 技术栈知识（JavaSE/JavaEE/Java 库/Java 工具/Java 框架）可以在 [java-stack](https://github.com/dunwu/java-stack) 了解。

## 规范

- 本项目的文档部分，遵循 [Gitbook](https://github.com/GitbookIO/gitbook) 规范，以便生成电子书。

- 推荐使用 [IDEA](https://www.jetbrains.com/idea/) 作为 IDE，自从使用了 IDEA，我就彻底放弃了 Eclipse 和 MyEclipse 等 IDE。

- 代码规范使用 [阿里巴巴Java开发手册](https://github.com/alibaba/p3c)

  如果你使用 IDEA 作为你的 IDE，推荐安装 Alibaba-Java-Coding-Guidelines 插件来做静态检查。
