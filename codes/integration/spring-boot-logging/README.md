# spring-boot-logging

本项目演示三种主流日志库的集成方式：

- logback
- log4j2
- log4j

## spring-boot-logging 使用方法

### 使用 logback 打印日志

（1）修改配置文件

修改 `application.properties` 文件中的 `spring.profiles.active = logback`

（2）指定 maven profile 编译

执行如下命令进行 maven 编译：

```
mvn clean package -DskipTests=true -Plogback
```

（3）启动 jar 进行测试

```
cd target
java -jar spring-boot-logging-xxx.jar
```

### 使用 log4j2 打印日志

（1）修改配置文件

修改 `application.properties` 文件中的 `spring.profiles.active = log4j2`

（2）指定 maven profile 编译

执行如下命令进行 maven 编译：

```
mvn clean package -DskipTests=true -Plog4j2
```

（3）启动 jar 进行测试

```
cd target
java -jar spring-boot-logging-xxx.jar
```

### 使用 log4j 打印日志

（1）修改配置文件

修改 `application.properties` 文件中的 `spring.profiles.active = log4j`

（2）指定 maven profile 编译

执行如下命令进行 maven 编译：

```
mvn clean package -DskipTests=true -Plog4j
```

（3）启动 jar 进行测试

```
cd target
java -jar spring-boot-logging-xxx.jar
```

## 集成说明

**Spring Boot 默认使用 logback 作为日志组件。**

Spring Boot 中集成日志组件的核心包是 `spring-boot-starter-logging`

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-logging</artifactId>
    </dependency>
```

由于 spring-boot-starter-logging 是 spring-boot-starter 默认引入的组件，而很多 Spring Boot 包都会引入 spring-boot-starter，所以要想集成其他日志组件，就需要将 `spring-boot-starter-logging` 一一识别出来并排除。

## logback

> Spring Boot + Slf4j + Logback

### 添加依赖

spring-boot-starter 默认使用 logback，无需额外引入依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
</dependency>
```

### logback.xml 配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>

<!-- logback中一共有5种有效级别，分别是TRACE、DEBUG、INFO、WARN、ERROR，优先级依次从低到高 -->
<configuration scan="true" scanPeriod="60 seconds" debug="false">

  <property name="DIR_NAME" value="spring-boot-log-logback"/>

  <!-- APPENDER BEGIN -->
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] [%-5p] %c{36}.%M - %m%n</pattern>
    </encoder>
  </appender>
  <appender name="ALL" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <!-- 根据时间来制定滚动策略 -->
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
      <fileNamePattern>${user.dir}/logs/${DIR_NAME}/all.%d{yyyy-MM-dd}.log</fileNamePattern>
      <maxHistory>30</maxHistory>
    </rollingPolicy>

    <!-- 根据文件大小来制定滚动策略 -->
    <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
      <maxFileSize>30MB</maxFileSize>
    </triggeringPolicy>

    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] [%-5p] %c{36}.%M - %m%n</pattern>
    </encoder>
  </appender>
  <appender name="SPRING" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <!-- 根据时间来制定滚动策略 -->
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
      <fileNamePattern>${user.dir}/logs/${DIR_NAME}/spring.%d{yyyy-MM-dd}.log
      </fileNamePattern>
      <maxHistory>30</maxHistory>
    </rollingPolicy>

    <!-- 根据文件大小来制定滚动策略 -->
    <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
      <maxFileSize>10MB</maxFileSize>
    </triggeringPolicy>

    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] [%-5p] %c{36}.%M - %m%n</pattern>
    </encoder>
  </appender>
  <!-- APPENDER END -->

  <!-- LOGGER BEGIN -->
  <!-- 本项目的日志记录，分级打印 -->
  <logger name="io.github.dunwu" level="DEBUG" additivity="false">
    <appender-ref ref="ALL"/>
    <appender-ref ref="STDOUT"/>
  </logger>
  <springProfile name="staging">
    <logger name="io.github.dunwu" level="TRACE" additivity="false">
      <appender-ref ref="ALL"/>
      <appender-ref ref="STDOUT"/>
    </logger>
  </springProfile>

  <!-- SPRING框架日志 -->
  <logger name="org.springframework" level="TRACE" additivity="false">
    <appender-ref ref="SPRING"/>
  </logger>

  <root level="WARN">
    <appender-ref ref="STDOUT"/>
  </root>
  <!-- LOGGER END -->

</configuration>
```

## log4j2

> Spring Boot + Slf4j + Log4j2

### 添加依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
  <exclusions>
    <exclusion>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-logging</artifactId>
    </exclusion>
  </exclusions>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

### log4j2.xml 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN" monitorInterval="30">
  <Properties>
    <Property name="PID">????</Property>
    <Property name="LOG_PATTERN">%clr{%d{yyyy-MM-dd HH:mm:ss.SSS}}{faint} %clr{%5p} %clr{${sys:PID}}{magenta} %clr{---}{faint} %clr{[%15.15t]}{faint} %clr{%-40.40c{1.}}{cyan} %clr{:}{faint} %m%n%xwEx
    </Property>
  </Properties>
  <Appenders>
    <Console name="Console" target="SYSTEM_OUT" follow="true">
      <PatternLayout pattern="${LOG_PATTERN}"/>
    </Console>
  </Appenders>
  <Loggers>
    <Logger name="org.springframework" level="warn"/>

    <Root level="info">
      <AppenderRef ref="Console"/>
    </Root>
  </Loggers>
</Configuration>

```

## log4j

> Spring Boot + Slf4j + Log4j

### 添加依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
  <exclusions>
    <exclusion>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-logging</artifactId>
    </exclusion>
  </exclusions>
</dependency>
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-api</artifactId>
</dependency>
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-log4j12</artifactId>
</dependency>
```

### log4j.xml 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j='http://jakarta.apache.org/log4j/'>
  <appender name="CONSOLE-APPENDER" class="org.apache.log4j.ConsoleAppender">
    <layout class="org.apache.log4j.PatternLayout">
      <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss,SSS\} [%-5p] [%t] %c{36\}.%M - %m%n"/>
    </layout>
    <filter class="org.apache.log4j.varia.LevelRangeFilter">
      <param name="levelMin" value="trace"/>
      <param name="levelMax" value="error"/>
      <param name="AcceptOnMatch" value="true"/>
    </filter>
  </appender>
  <appender name="ALL-APPENDER" class="org.apache.log4j.DailyRollingFileAppender">
    <param name="File" value="logs/spring-boot-integration-log4j.log"/>
    <param name="Append" value="true"/>
    <param name="DatePattern" value="'.'yyyy-MM-dd'.log'"/>
    <layout class="org.apache.log4j.PatternLayout">
      <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss,SSS\} [%-5p] [%t] %c{36\}.%M - %m%n"/>
    </layout>
    <filter class="org.apache.log4j.varia.LevelRangeFilter">
      <param name="AcceptOnMatch" value="true"/>
    </filter>
  </appender>

  <logger name="io.github.dunwu.springboot" additivity="false">
    <appender-ref ref="ALL-APPENDER"/>
  </logger>

  <root>
    <appender-ref ref="CONSOLE-APPENDER"/>
  </root>
</log4j:configuration>
```

## 使用 Slf4j API

由于 logback、log4j、log4j2 都可以通过 slf4j 统一输出格式，所以实际上在代码层面输出形式完全一致：

```java
public class WebLogAspect {

    private Logger logger = Logger.getLogger(getClass());

    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }
}
```
