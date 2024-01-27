# spring-boot-data-jpa

> **本项目演示 Spring Boot + JPA 访问关系型数据库，支持基本的 CRUD 操作以及直接支持 REST 接口方式访问数据。**
>
> 本项目默认使用 Mysql 数据库，测试环境使用 H2 数据库。
>
> 依赖环境：
>
> ![mysql](https://img.shields.io/badge/mysql-8.0-blue) ![h2](https://img.shields.io/badge/h2-1.4.199-blue) ![jdk](https://img.shields.io/badge/jdk-1.8.0__181-blue) ![maven](https://img.shields.io/badge/maven-v3.6.0-blue)

## 使用说明

### 创建数据库

执行以下 sql 创建数据库 `spring_boot_tutorial`

```sql
DROP DATABASE IF EXISTS spring_boot_tutorial;
CREATE DATABASE spring_boot_tutorial;
```

### 修改数据源

修改 `application.properties` 文件中的数据源为真实的数据源。

> 注意：Mysql8 和 Mysql5 客户端驱动的驱动类是不同的。

```properties
spring.datasource.url = jdbc:mysql://localhost:3306/spring_tutorial?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8
spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
spring.datasource.username = root
spring.datasource.password = root
```

### 构建

在项目根目录下执行 maven 指令。

```
mvn clean package -DskipTests=true
```

### 启动

```
cd target
java -jar spring-boot-data-jpa-1.0.0.jar
```

启动后，应该会看到控制台打印类似如下信息：

```java
... ...
20:22:33.401 [main] [INFO ] i.g.d.s.SpringBootDataJpaApplication$$EnhancerBySpringCGLIB$$a0a040c6.printDataSourceInfo - DataSource Url: jdbc:mysql://localhost:3306/spring_tutorial?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
20:22:33.402 [main] [INFO ] i.g.d.s.SpringBootDataJpaApplication$$EnhancerBySpringCGLIB$$a0a040c6.run - Connect to datasource success.
  ... ...
```

应用启动后，会自动根据 `resources/sql` 目录下的 sql 初始化数据。

检查数据库会发现，在数据库 `spring_boot_tutorial` 中新建了一张 `user` 表，并插入了 2 条数据。

### 访问 JPA REST

打开浏览器，访问 http://localhost:8080/user 可以看到，JPA 帮我们自动生成了 REST 应答内容。

![image-20191118203305320](https://raw.githubusercontent.com/dunwu/images/master/snap/image-20191118203305320.png)

这个
REST
接口是根据本项目中的 `io.github.dunwu.springboot.data.AccountRepository`
类中的如下代码定义自动生成的：

```java
@RepositoryRestResource(collectionResourceRel = "user", path = "user")public interface UserRepository extends JpaRepository<User, Long> {
```

### 单元测试

- 执行 `SpringBootDataJpaTest` 可以更直观的看到 JPA 模式下的 CRUD 操作。
- 执行 `SpringBootDataJpaRestTest` 可以更直观的看到 REST JPA 模式下的 CRUD 操作。

## 扩展学习

- [Mysql 教程](https://dunwu.github.io/db-tutorial/#/sql/mysql/README)
- [H2 入门指南](https://dunwu.github.io/db-tutorial/#/sql/h2)
