# spring-boot-data-elasticsearch

> **本项目演示 Spring Boot + JDBC 访问关系型数据库，执行基本的 CRUD 操作。**
>
> **_本项目只适用于 Mysql_**，其他关系型数据库可能会因为 SQL 语法问题不能访问。
>
> 依赖环境：
>
> ![mysql](https://img.shields.io/badge/mysql-8.0-blue) ![jdk](https://img.shields.io/badge/jdk-1.8.0__181-blue) ![maven](https://img.shields.io/badge/maven-v3.6.0-blue)

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
java -jar spring-boot-data-jdbc-1.0.0.jar
```

启动后，应该会看到控制台打印类似如下信息：

```java
... ...
18:11:21.895 [main] [INFO ] i.g.d.s.SpringBootDataJdbcApplication$$EnhancerBySpringCGLIB$$605038c2.printDataSourceInfo - DataSource Url: jdbc:mysql://localhost:3306/spring_tutorial?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8
18:11:21.896 [main] [INFO ] i.g.d.s.SpringBootDataJdbcApplication$$EnhancerBySpringCGLIB$$605038c2.run - Connect to datasource success.
18:11:21.960 [main] [INFO ] i.g.d.s.SpringBootDataJdbcApplication$$EnhancerBySpringCGLIB$$605038c2.lambda$run$0 - User(id=1, username=admin, password=$2a$10$Y9uV9YjFuNlATDGz5MeTZeuo8LbebbpP6jRgtZYQcgiCZRlf8rJYG, email=admin@xxx.com)
18:11:21.960 [main] [INFO ] i.g.d.s.SpringBootDataJdbcApplication$$EnhancerBySpringCGLIB$$605038c2.lambda$run$0 - User(id=2, username=user, password=$2a$10$Y9uV9YjFuNlATDGz5MeTZeuo8LbebbpP6jRgtZYQcgiCZRlf8rJYG, email=user@xxx.com)
  ... ...
```

应用启动后，会自动根据 `resources/sql` 目录下的 sql 初始化数据。

检查数据库会发现，在数据库 `spring_boot_tutorial` 中新建了一张 `user` 表，并插入了 2 条数据。

### 单元测试

执行 `SpringBootDataJdbcTest` 可以更直观的看到 JDBC 模式下的 CRUD 操作。

## 版本兼容

|                                             Spring Data Release Train                                              |                                        Spring Data Elasticsearch                                         | Elasticsearch | Spring Framework | Spring Boot |
| :----------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------: | :-----------: | :--------------: | :---------: |
|                                                    2021.2 (Raj)                                                    |                                                  4.4.x                                                   |    7.17.6     |      5.3.x       |    2.7.x    |
|                                                     2021.1 (Q)                                                     |                                                  4.3.x                                                   |    7.15.2     |      5.3.x       |    2.6.x    |
|                                                  2021.0 (Pascal)                                                   | 4.2.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |    7.12.0     |      5.3.x       |    2.5.x    |
| 2020.0 (Ockham)[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] | 4.1.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |     7.9.3     |      5.3.2       |    2.4.x    |
|     Neumann[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)]     | 4.0.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |     7.6.2     |      5.2.12      |    2.3.x    |
|      Moore[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)]      | 3.2.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |    6.8.12     |      5.2.12      |    2.2.x    |
|    Lovelace[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)]     | 3.1.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |     6.2.2     |      5.1.19      |    2.1.x    |
|       Kay[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)]       | 3.0.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |     5.5.0     |      5.0.13      |    2.0.x    |
|     Ingalls[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)]     | 2.1.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |     2.4.0     |      4.3.25      |    1.5.x    |
