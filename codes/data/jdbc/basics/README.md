# spring-boot-data-jdbc

> **本项目演示 Spring Boot 以 JDBC 方式访问关系型数据库，通过 `JdbcTemplate` 执行基本的 CRUD 操作。**

## 使用说明

### 创建数据库

执行以下 sql 创建数据库 `spring_tutorial`

```sql
DROP DATABASE IF EXISTS spring_tutorial;
CREATE DATABASE spring_tutorial;
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
18:11:21.895 [main] [INFO ] i.g.d.s.DataJdbcApplication$$EnhancerBySpringCGLIB$$605038c2.printDataSourceInfo - DataSource Url: jdbc:mysql://localhost:3306/spring_tutorial?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8
18:11:21.896 [main] [INFO ] i.g.d.s.DataJdbcApplication$$EnhancerBySpringCGLIB$$605038c2.run - Connect to datasource success.
18:11:21.960 [main] [INFO ] i.g.d.s.DataJdbcApplication$$EnhancerBySpringCGLIB$$605038c2.lambda$run$0 - User(id=1, username=admin, password=$2a$10$Y9uV9YjFuNlATDGz5MeTZeuo8LbebbpP6jRgtZYQcgiCZRlf8rJYG, email=admin@xxx.com)
18:11:21.960 [main] [INFO ] i.g.d.s.DataJdbcApplication$$EnhancerBySpringCGLIB$$605038c2.lambda$run$0 - User(id=2, username=user, password=$2a$10$Y9uV9YjFuNlATDGz5MeTZeuo8LbebbpP6jRgtZYQcgiCZRlf8rJYG, email=user@xxx.com)
  ... ...
```

应用启动后，会自动根据 `resources/sql` 目录下的 sql 初始化数据。

检查数据库会发现，在数据库 `spring_tutorial` 中新建了一张 `user` 表，并插入了 2 条数据。

### 单元测试

执行 `io.github.dunwu.springboot.data.DataJdbcTests` 可以更直观的看到 JDBC 模式下的 CRUD 操作。``
