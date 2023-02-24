# 说明

> 第一个 Spring mvc 示例。

![img](https://raw.githubusercontent.com/dunwu/images/dev/snap/20190905225429.png)

| 库或工具             | 版本            | 说明                                                                                                                                                                                                          |
| -------------------- | --------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Spring Framework     | 5.0.2.RELEASE   |                                                                                                                                                                                                               |
| JDK                  | 1.8+            | Spring5 开始，要求 JDK8+                                                                                                                                                                                      |
| Maven                | 3.5.2           | 本项目使用 [maven](https://maven.apache.org/index.html) 作为构建工具。                                                                                                                                        |
| jetty-maven-plugin   | 9.4.8.v20171121 | [Jetty](http://www.eclipse.org/jetty/) 可作为 web 服务器和 servlet 容器。此插件可以免部署启动 web app。                                                                                                       |
| tomcat7-maven-plugin | 2.2             | [Tomcat](https://tomcat.apache.org/index.html) 可作为 web 服务器和 servlet 容器。此插件可以免部署启动 web app。Tomcat 早已经发布 Tomcat8，但是 maven 插件 一直没有提供 tomcat8 的支持（最后更新为 2013 年）。 |

### 启动

为了便于展示示例，所有 war 包形式的项目都可以使用 maven 插件快速启动嵌入式服务器，支持 Tomcat 和 Jetty 两种方式。

Tomcat7 嵌入式服务器启动：

```bash
$ mvn tomcat7:run -Dmaven.test.skip=true
```

Jetty 嵌入式服务器启动：

```bash
$ mvn jetty:run -Dmaven.test.skip=true
```

如果子项目中无特殊说明，默认端口配置为 8089，当然，你可以自己指定。

启动成功后，访问 http://localhost:8089 。
