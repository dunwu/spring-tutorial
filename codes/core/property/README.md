# spring-boot-property

> **本项目演示 Spring Boot 加载配置属性。**
>
> 本项目中全方位的演示了 Spring Boot 加载属性的方式：
>
> - @Value
>
> - @ConfigurationProperties
>
> - Environment
>
>
> 依赖环境：
>
> ![jdk](https://img.shields.io/badge/jdk-1.8.0__181-blue) ![maven](https://img.shields.io/badge/maven-v3.6.0-blue)

## 使用说明

### 要点

#### 属性绑定

Spring Boot 支持 properties 和 yaml 两种文件格式的属性加载。

为此，本项目中的 `application-prop.properties` 和 `application-yaml.yaml` 文件中定义了几乎一模一样的属性，这些属性绑定 `DunwuProperties` 类的属性。

可以通过在 `application.properties` 文件中指定 `spring.profiles.active` 属性值为 prop 或 yaml 来灵活加载 application 属性文件。

#### 明确指定属性文件名

如果我们需要加载 Spring Boot 默认属性文件以外的文件，可以使用 `@PropertySource` 注解来指定。参考 `CustomConfig` 类和它的绑定属性文件 `prop/custom.properties`。

#### 随机属性

Spring Boot 内置了随机属性，参考 `DunwuRandomProperties` 类和它的绑定属性文件 `prop/random.properties`。

#### 自定义属性校验器

自定义属性校验器 `ValidatedPropertiesValidator`，对 `ValidatedProperties` 类的属性进行校验。

### 构建

在项目根目录下执行 maven 指令。

```
mvn clean package -DskipTests=true
```

### 启动

```
cd target
java -jar spring-boot-property-1.0.0.jar
```

启动后，应该会看到控制台打印类似如下信息：

```java
... ...
14:40:18.163 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - validator.host: 127.0.0.1
14:40:18.165 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - validator.port: 8080
14:40:18.182 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - profile: prop
14:40:18.183 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - ID: 1
14:40:18.183 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - 作者姓名: Zhang Peng
14:40:18.184 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - 性别: MALE
14:40:18.184 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - 日期: 2019-11-20 12:00:00
14:40:18.184 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - 邮件: forbreak@163.com
14:40:18.184 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - =========== 兴趣 ===========
14:40:18.185 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.forEach - Music
14:40:18.185 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.forEach - Game
14:40:18.185 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.forEach - Reading
14:40:18.185 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - =========== 信息 ===========
14:40:18.186 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.lambda$run$0 - education : master's degree
14:40:18.186 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.lambda$run$0 - career : programmer
14:40:18.186 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - =========== 技能 ===========
14:40:18.187 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.lambda$run$1 - JavaCore 技术项：
14:40:18.187 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.forEach - JDK
14:40:18.187 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.forEach - JVM
14:40:18.188 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.lambda$run$1 - JavaWeb 技术项：
14:40:18.188 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.forEach - Spring
14:40:18.188 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.forEach - Spring Boot
14:40:18.188 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.forEach - MyBatis
14:40:18.188 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - custom.topic1: Java
14:40:18.188 [main] [INFO ] i.g.d.s.SpringBootPropertyApplication$$EnhancerBySpringCGLIB$$da0854f.run - custom.topic2: [Java] Spring Boot
  ... ...
```

### 单元测试

执行 `SpringBootPropertyApplicationTest`  可以更直观的看到加载属性的操作。

## 扩展学习

-  [SpringBoot 教程之属性加载详解](https://dunwu.github.io/spring-boot-tutorial/#/spring-boot-property)
