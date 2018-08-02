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

spring-notes 参考 Spring 官方的技术划分，将技术内容分为以下部分：

**Core**

> Spring 框架的核心技术。如；IOC 依赖注入、AOP、数据绑定等。

**| [CODES](https://github.com/dunwu/spring-notes/tree/master/codes/core) | [DOCS](https://github.com/dunwu/spring-notes/tree/master/docs/spring/core) |**

**Data**

> Spring 在数据库领域的应用。如：JDBC、ORM、事务等。

**| [CODES](https://github.com/dunwu/spring-notes/tree/master/codes/data) | [DOCS](https://github.com/dunwu/spring-notes/tree/master/docs/spring/data) |**

**Web**

> Spring 在 web 领域的应用。如：Spring MVC、WebSocket 等。

**| [CODES](https://github.com/dunwu/spring-notes/tree/master/codes/web) | [DOCS](https://github.com/dunwu/spring-notes/tree/master/docs/spring/web) |**

**Integration**

> Spring 与第三方框架、库集成。如：Cache、Scheduling、JMS、JMX 等。

**| [CODES](https://github.com/dunwu/spring-notes/tree/master/codes/integration) | [DOCS](https://github.com/dunwu/spring-notes/tree/master/docs/spring/integration) |**

**Security**

> Spring 在安全领域的应用。如：认证、授权 等。

**| [CODES](https://github.com/dunwu/spring-notes/tree/master/codes/security) |**

**Example**

> Spring 实战示例。

**| [CODES](https://github.com/dunwu/spring-notes/tree/master/codes/example) |**

**面试**

> Spring 面试问题 TOP 50

**| [DOCS](https://github.com/dunwu/spring-notes/tree/master/docs/spring/spring-interview.md) |**

## 说明

### 环境说明

**重要库版本**

| 库或工具             | 版本            | 说明                                                                                                                                                                                                          |
| -------------------- | --------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Spring Framework     | 5.0.2.RELEASE   |                                                                                                                                                                                                               |
| JDK                  | 1.8+            | Spring5 开始，要求 JDK8+                                                                                                                                                                                      |
| Maven                | 3.5.2           | 本项目使用 [maven](https://maven.apache.org/index.html) 作为构建工具。                                                                                                                                        |
| jetty-maven-plugin   | 9.4.8.v20171121 | [Jetty](http://www.eclipse.org/jetty/) 可作为 web 服务器和 servlet 容器。此插件可以免部署启动 web app。                                                                                                       |
| tomcat7-maven-plugin | 2.2             | [Tomcat](https://tomcat.apache.org/index.html) 可作为 web 服务器和 servlet 容器。此插件可以免部署启动 web app。Tomcat 早已经发布 Tomcat8，但是 maven 插件 一直没有提供 tomcat8 的支持（最后更新为 2013 年）。 |

### 项目说明

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

> :point_right: [**spring-notes 文档在线阅读**](https://dunwu.gitbooks.io/spring-notes/)
>
> 扩展学习：更多 Java 技术栈知识（JavaSE/JavaEE/Java 库/Java 工具/Java 框架）可以在 [java-stack](https://github.com/dunwu/java-stack) 了解。

## 规范

- 本项目的文档部分，遵循 [Gitbook](https://github.com/GitbookIO/gitbook) 规范，以便生成电子书。

- 推荐使用 [IDEA](https://www.jetbrains.com/idea/) 作为 IDE

- 代码规范使用 [阿里巴巴 Java 开发手册](https://github.com/alibaba/p3c)

  如果你使用 IDEA 作为你的 IDE，推荐安装 Alibaba-Java-Coding-Guidelines 插件来做静态检查。
