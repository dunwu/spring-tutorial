# spring-tutorial-websocket

## 说明

### 环境

依赖 jar 包：

```
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-messaging</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-websocket</artifactId>
</dependency>
```

服务器：

Tomcat7

### 运行

Tomcat7 嵌入式服务器启动：

```
$ mvn tomcat7:run
```

访问 http://localhost:8089/spring-tutorial-websocket/index.jsp 可以尝试 websocket 点对点通信

访问 http://localhost:8089/spring-tutorial-websocket/msg/broadcast 可以尝试 websocket 广播通信，服务器会向所有用户发送广播消息。

## 问题

spring-websocket 和 jetty 9.3 版本存在兼容性问题，Tomcat则木有问题。

stackoverflow 上就有人提出问题：[stackoverflow issue](https://stackoverflow.com/questions/39477012/spring-web-socket-incompatible-with-jetty9-3) ，但是没有解决。

我尝试了好几遍，没有找到解决方案。友情提醒，在 jetty 中请慎用 spring-websocket ，还是老老实实使用

下面两个jar包吧

```xml
<dependency>
  <groupId>javax.websocket</groupId>
  <artifactId>javax.websocket-api</artifactId>
  <scope>provided</scope>
</dependency>
<dependency>
  <groupId>org.eclipse.jetty.websocket</groupId>
  <artifactId>javax-websocket-server-impl</artifactId>
</dependency>
```

