# spring-boot-data-jdbc-multi-datasource

> **本项目演示 Spring Boot + JDBC 访问多数据源。**
>
> 应用开发中，有时存在这样的场景：项目中需要多种数据源，有可能是两个数据库服务器，有可能是两种完全不用的服务器。
>
> 在 Spring Boot 中接入多种数据源很简便。
>
> 本项目中支持同时访问 Mysql 和 H2 两种数据源。
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
# datasource01
spring.datasource.mysql.jdbc-url = jdbc:mysql://localhost:3306/spring_tutorial?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
spring.datasource.mysql.driver-class-name = com.mysql.cj.jdbc.Driver
spring.datasource.mysql.username = root
spring.datasource.mysql.password = root
# datasource02
spring.datasource.h2.jdbc-url = jdbc:h2:mem:test
spring.datasource.h2.driver-class-name = org.h2.Driver
spring.datasource.h2.username = sa
spring.datasource.h2.password =
```

如上所示：与单一数据源锁不同的是在 spring.datasource 关键字后，增加一个关键字以区分不同数据源。

### 数据源属性配置

与属性配置文件所对应的是，在配置类中加载 spring.datasource.mysql 和 spring.datasource.h2 开头的属性，作为不同数据源的属性配置。

> 注意：必须使用 `@Primary` 注解标记一个同类型的 Bean 作为首选 Bean。

```java
@Configuration
public class DataSourceConfig {

	@Primary
	@Bean("mysqlDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.mysql")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean("mysqlJdbcTemplate")
	public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean("h2DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.h2")
	public DataSource h2DataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "h2JdbcTemplate")
	public JdbcTemplate h2JdbcTemplate(@Qualifier("h2DataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
```

### 构建

在项目根目录下执行 maven 指令。

```
mvn clean package -DskipTests=true
```

### 启动

```
cd target
java -jar spring-boot-data-jdbc-multi-datasource-1.0.0.jar
```

启动后，应该会看到控制台打印类似如下信息：

```java
... ...
21:07:30.616 [main] [INFO ] i.g.d.s.DataJdbcMultiDataSourceApplication.printDataSourceInfo - DataSource Url: jdbc:mysql://localhost:3306/multi_connectors_db1?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
21:07:30.617 [main] [INFO ] i.g.d.s.DataJdbcMultiDataSourceApplication.run - Connect to mysql datasource success.
21:07:30.762 [main] [INFO ] i.g.d.s.DataJdbcMultiDataSourceApplication.printDataSourceInfo - DataSource Url: jdbc:h2:mem:test
21:07:30.762 [main] [INFO ] i.g.d.s.DataJdbcMultiDataSourceApplication.run - Connect to h2 datasource success.
  ... ...
```

应用启动后，会自动根据 `resources/sql` 目录下的 sql 初始化数据。

检查数据库会发现，在数据库 `spring_boot_tutorial` 中新建了一张 `user` 表，并插入了 2 条数据。

### 单元测试

分别执行下面两个单元测试类，体验在同一个项目中访问多数据源的 CRUD 操作。

- 执行 `DataJdbcMysqlDataSourceTest` 。
- 执行 `DataJdbcH2DataSourceTest` 。

## 扩展学习

- [Mysql 教程](https://dunwu.github.io/db-tutorial/#/sql/mysql/README)
- [H2 入门指南](https://dunwu.github.io/db-tutorial/#/sql/h2)
