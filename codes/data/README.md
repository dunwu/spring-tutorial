# Spring Boot 数据篇

> 本目录下的子项目展示 Spring Boot 集成各种数据源的示例。

（1）JDBC

| 项目类型   | 示例                                                                                                                                                  | 说明                                                                                 |
| ---------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------ |
| Spring     | [spring-data-jdbc](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/jdbc/spring-data-jdbc)                                             | Spring 以 JDBC 方式访问关系型数据库，通过 `JdbcTemplate` 执行基本的 CRUD 操作。      |
| SpringBoot | [spring-boot-data-jdbc](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/jdbc/spring-boot-data-jdbc)                                   | Spring Boot 以 JDBC 方式访问关系型数据库，通过 `JdbcTemplate` 执行基本的 CRUD 操作。 |
| SpringBoot | [spring-boot-data-jdbc-druid](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/jdbc/spring-boot-data-jdbc-druid)                       | SpringBoot 使用 [Druid](https://github.com/alibaba/druid) 作为数据库连接池。         |
| SpringBoot | [spring-boot-data-jdbc-multi-datasource](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/jdbc/spring-boot-data-jdbc-multi-datasource) | SpringBoot 连接多数据源。本示例中同时连接 Mysql 和 H2。                              |

（2）ORM

| 项目类型   | 示例                                                                                                                                                       | 说明                                                                                                                                                                                                         |
| ---------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Spring     | [spring-data-mybatis](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/orm/spring-data-mybatis)                                             | Spring 使用 [MyBatis](https://github.com/mybatis/mybatis-3) 作为 ORM 框架访问数据库示例。                                                                                                                    |
| SpringBoot | [spring-boot-data-mybatis-mapper](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/orm/spring-boot-data-mybatis-mapper)                     | SpringBoot 使用 [MyBatis](https://github.com/mybatis/mybatis-3) + [Mapper](https://github.com/abel533/Mapper) + [PageHelper](https://github.com/pagehelper/Mybatis-PageHelper) 作为 ORM 框架访问数据库示例。 |
| SpringBoot | [spring-boot-data-mybatis-plus](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/orm/spring-boot-data-mybatis-plus)                         | SpringBoot 使用 [MyBatis Plus](https://github.com/baomidou/mybatis-plus) 作为 ORM 框架访问数据库示例。                                                                                                       |
| SpringBoot | [spring-boot-data-mybatis-multi-datasource](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/orm/spring-boot-data-mybatis-multi-datasource) | SpringBoot 连接多数据源，并使用 [MyBatis Plus](https://github.com/baomidou/mybatis-plus) 作为 ORM 框架访问数据库示例。                                                                                       |
| SpringBoot | [spring-boot-data-jpa](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/orm/spring-boot-data-jpa)                                           | SpringBoot 使用 JPA 作为 ORM 框架访问数据库示例。                                                                                                                                                            |

（3）Nosql 数据库

| 项目类型   | 示例                                                                                                                                   | 说明                                                                              |
| ---------- | -------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------- |
| Spring     | [spring-data-nosql](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/nosql/spring-data-nosql)                           | Spring 访问各种 NoSQL 的示例。                                                    |
| SpringBoot | [spring-boot-data-redis](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/nosql/spring-boot-data-redis)                 | SpringBoot 访问 [Redis](https://redis.io/) 单节点、集群的示例。                   |
| SpringBoot | [spring-boot-data-mongodb](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/nosql/spring-boot-data-mongodb)             | SpringBoot 访问 [MongoDB](https://www.mongodb.com/) 的示例。                      |
| SpringBoot | [spring-boot-data-elasticsearch](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/nosql/spring-boot-data-elasticsearch) | SpringBoot 访问 [Elasticsearch](https://www.elastic.co/guide/index.html) 的示例。 |
| SpringBoot | [spring-boot-data-hdfs](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/nosql/spring-boot-data-hdfs)                   | SpringBoot 访问 HDFS 的示例。                                                     |

（4）缓存

| 项目类型   | 示例                                                                                                                                     | 说明                                                                                 |
| ---------- | ---------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------ |
| SpringBoot | [spring-boot-data-cache](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/cache/spring-boot-data-cache)                   | SpringBoot 默认缓存框架的示例。                                                      |
| SpringBoot | [spring-boot-data-cache-j2cache](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/cache/spring-boot-data-cache-j2cache)   | SpringBoot 使用 [j2cache](https://gitee.com/ld/J2Cache) 作为缓存框架的示例。         |
| SpringBoot | [spring-boot-data-cache-jetcache](https://github.com/dunwu/spring-tutorial/tree/master/codes/data/cache/spring-boot-data-cache-jetcache) | SpringBoot 使用 [jetcache](https://github.com/alibaba/jetcache) 作为缓存框架的示例。 |
