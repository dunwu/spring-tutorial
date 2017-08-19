# spring-notes
Spring 学习笔记。

以简单范例来展示 spring 在 web 开发中的各种应用。

## 内容

- **spring-notes-helloworld**：spring第一个简单例子。

- **spring-notes-ioc**：spring的依赖注入范例。

- **spring-notes-aop**：spring的切面编程范例。

- **spring-notes-db**：spring 集成不同数据源的范例。

- **spring-notes-orm**：spring 集成 orm 框架的范例。

  hibernate 和 mybatis 都是比较流行的 orm 框架。

  hibernate 功能更强大，但是也更复杂，学习周期更长，数据调优也更为复杂。

  相对于新手，mybatis 更容易上手，因此这里选用 mybatis 来展示如何集成。

- **spring-notes-rmi**：spring的远程调用范例。分为server端和client端。

- **spring-notes-scheduler**：spring 的调度任务范例。

- **spring-notes-sccurity**：spring 的安全框架范例。

  spring 自身的安全框架 spring-security 相比 shiro 较为重型化，并且 shiro 可以满足大部分的应用，所以这里选用 shiro。

- **spring-notes-validator**：spring 的校验器范例。自定义一个简单的校验器。

- **spring-notes-mvc**：将展示 spring mvc 的特性。

- **spring-notes-boot**：spring-notes-boot 是 spring 的快速构建框架。

- **spring-notes-common**：spring-common 是一个工具集。

## 说明

### 项目结构

- **codes**：代码目录。
- **docs**：文档目录。

### 使用说明

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

## 约定

- 本项目的文档部分，遵循 [Gitbook](https://github.com/GitbookIO/gitbook) 规范，以便生成电子书。