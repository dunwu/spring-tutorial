<p align="center">
    <a href="https://spring.io/projects/spring-framework" target="_blank" rel="noopener noreferrer">
        <img src="http://dunwu.test.upcdn.net/common/logo/spring.png" alt="spring-logo">
    </a>
</p>

<p align="center">
    <a href="https://creativecommons.org/licenses/by-sa/4.0/" target="_blank" rel="noopener noreferrer">
        <img src="https://badgen.net/github/license/dunwu/spring-tutorial">
    </a>
    <img alt="Spring Boot Version" src="https://img.shields.io/badge/spring-5.0.2.RELEASE-blue">
</p>

<h1 align="center">Spring Tutorial</h1>

> **`spring-tutorial`** 是一个以简单范例来展示 spring 在 web 开发中的各种应用的教程。
>
> - 🔁 项目同步维护：[Github](https://github.com/dunwu/spring-tutorial/) | [Gitee](https://gitee.com/turnon/spring-tutorial/)
>- 📖 电子书阅读：[Github Pages](https://dunwu.github.io/spring-tutorial/) | [Gitee Pages](http://turnon.gitee.io/spring-tutorial/)

|      0️⃣      |      1️⃣      |      2️⃣      |     3️⃣     |      4️⃣      |      5️⃣      |
| :-----------: | :-----------: | :-----------: | :---------: | :-----------: | :-----------: |
| [综合](#综合) | [核心](#核心) | [数据](#数据) | [Web](#Web) | [集成](#集成) | [安全](#安全) |

## 综合

- [Spring 面经](spring-interview.md)
- [Spring 概述](spring-overview.md)

## 核心

> [核心](core/README.md) 章节主要针对：Spring 框架的核心技术。如；IOC 依赖注入、AOP、数据绑定等。

- [Spring IoC](core/ioc.md)
- [Spring AOP](core/aop.md)
- [Spring Resources](core/spring-and-resources.md)

## 数据

> [数据](data/README.md) 章节主要针对：Spring 在数据库领域的应用。如：JDBC、ORM、事务等。

- [Spring 的数据访问策略](data/data-access-in-spring.md)
- [Spring 中使用 JDBC 访问数据](data/spring-and-jdbc.md)
- [事务管理](data/transaction.md)

## Web

> [Web](web/README.md) 章节主要针对：Spring 在 web 领域的应用。如：Spring MVC、WebSocket 等。

- [SpringMVC 简介](web/spring-mvc-introduction.md)

## 集成

> [集成](integration/README.md) 章节主要针对：Spring 与第三方框架、库集成。如：Cache、Scheduling、JMS、JMX 等。

- [Spring 集成 Dubbo](integration/spring-and-dubbo.md)
- [Spring 集成缓存](integration/spring-and-cache.md)
- [Spring 集成调度器](integration/spring-and-scheduler.md)

## 安全

> Spring 在安全领域的应用。如：认证、授权 等。

## [附录](appendix/README.md)

- [资源](appendix/resources.md)
- [spring 4 升级踩雷指南](appendix/spring4-upgrade.md)

## 教程说明

### 环境

**重要库版本**

| 库或工具             | 版本            | 说明                                                                                                                                                                                                          |
| -------------------- | --------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Spring Framework     | 5.0.2.RELEASE   |                                                                                                                                                                                                               |
| JDK                  | 1.8+            | Spring5 开始，要求 JDK8+                                                                                                                                                                                      |
| Maven                | 3.5.2           | 本项目使用 [maven](https://maven.apache.org/index.html) 作为构建工具。                                                                                                                                        |
| jetty-maven-plugin   | 9.4.8.v20171121 | [Jetty](http://www.eclipse.org/jetty/) 可作为 web 服务器和 servlet 容器。此插件可以免部署启动 web app。                                                                                                       |
| tomcat7-maven-plugin | 2.2             | [Tomcat](https://tomcat.apache.org/index.html) 可作为 web 服务器和 servlet 容器。此插件可以免部署启动 web app。Tomcat 早已经发布 Tomcat8，但是 maven 插件 一直没有提供 tomcat8 的支持（最后更新为 2013 年）。 |

### 项目

- **`/codes`**：代码目录。
- **`/docs`**：文档目录。

#### codes 使用说明

为了便于展示示例，所有 war 包形式的项目都可以使用 maven 插件快速启动嵌入式服务器，支持 Tomcat 和 Jetty 两种方式。

Tomcat7 嵌入式服务器启动：

```bash
$ mvn tomcat7:run -Dmaven.test.skip=true
```

Jetty 嵌入式服务器启动：

```bash
$ mvn jetty:run -Dmaven.test.skip=true
```

> 如果子项目中无特殊说明，默认端口配置为 8089，当然，你可以自己指定。
>
> 启动成功后，访问 http://localhost:8089 。

#### docs 使用说明

`/docs` 遵循 [Gitbook](https://github.com/GitbookIO/gitbook) 规范，可以生成静态 html 电子书。

> :point_right: [**spring-tutorial 文档在线阅读**](https://dunwu.github.io/spring-tutorial/)
>
> 扩展学习：更多 Java 技术栈知识（JavaSE/JavaEE/Java 库/Java 工具/Java 框架）可以在 [java-stack](https://github.com/dunwu/java-stack) 了解。

### 规范

- 推荐使用 [IDEA](https://www.jetbrains.com/idea/) 作为 IDE
- 代码规范使用 [阿里巴巴 Java 开发手册](https://github.com/alibaba/p3c)
  - 如果你使用 IDEA 作为你的 IDE，推荐安装 Alibaba-Java-Coding-Guidelines 插件来做静态检查。
