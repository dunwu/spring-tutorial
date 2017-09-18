# spring-notes-data

> 展示 Spring 的数据访问技术

## spring-jdbc

> 本项目当前展示了对于H2/Mysql的JDBC操作。

### 使用说明

- `H2JdbcTest` 测试了数据源为 H2 的 JDBC 操作。

- `MysqlJdbcTest` 测试了数据源为 Mysql 的 JDBC 操作。

- `DruidJdbcTest` 展示使用 Druid 建立数据源连接池。


## spring-orm

展示 Spring + Mybatis 集成的完整示例。

并引入了 Mybatis Generator，可以根据数据库自动生成 Mybatis 的数据访问样板式代码。

## TODO

`io.github.zp1024.spring.data.db` 中的内容应该是 spring-data 的内容，以后会迁出。

待完成 Spring 事务管理实例：spring-notes-data-tx
