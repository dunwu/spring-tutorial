# Spring Boot 数据篇

> 本目录下的子项目展示 Spring Boot 集成各种数据源的示例。

- **关系型数据库**
  - [spring-boot-data-jdbc](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-jdbc) - 演示 Spring Boot + JDBC 访问关系型数据库，执行基本的 CRUD 操作。
  - [spring-boot-data-jdbc-multi-datasource](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-jdbc-multi-datasource) - 演示 Spring Boot + JDBC 访问多数据源（可以是多个数据库服务器，也可以是多个截然不同的数据库）。
  - [spring-boot-data-jpa](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-jpa) - 演示 Spring Boot + JPA 访问关系型数据库，支持基本的 CRUD 操作以及直接支持 REST 接口方式访问数据。
  - [spring-boot-data-mybatis-multi-datasource](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-mybatis-multi-datasource) - Spring Boot + Mybatis Plus 访问多数据源。
  - [spring-boot-data-mybatis-plus](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-mybatis-plus) - Spring Boot + Mybatis Plus 访问数据。
  - [spring-boot-data-flyway](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-flyway) - Spring Boot 中通过 flyway 控制 sql 版本。
  - [spring-boot-data-mybatis-sharding](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-mybatis-sharding) - Spring Boot + Mybatis Plus + ShardingSphere (sharding-jdbc) 访问分库分表数据。
- **Nosql 数据库**
  - [spring-boot-data-redis](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-redis) - 展示 Spring Boot 中访问 Redis。
  - [spring-boot-data-redis-cluster](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-redis-cluster) - 展示 Spring Boot 中访问 Redis 集群。
  - [spring-boot-data-mongodb](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-mongodb) - 展示 Spring Boot 中访问 MongoDB。
  - [spring-boot-data-elasticsearch](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-elasticsearch) - 展示 Spring Boot 中访问 elasticsearch 数据（TCP 方式访问 9300 服务端口）。
  - [spring-boot-data-elasticsearch-jest](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-elasticsearch-jest) - 展示 Spring Boot 中访问 elasticsearch 数据（HTTP 方式访问 9200 服务端口）。
- **缓存**
  - [spring-boot-data-cache](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-cache) - 展示 Spring Boot 中如何使用简单的应用缓存。
  - [spring-boot-data-cache-j2cache](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-cache-j2cache) - 展示 Spring Boot + Jetcache 实现分布式二级缓存。
  - [spring-boot-data-cache-jetcache](https://github.com/dunwu/spring-boot-tutorial/tree/master/codes/data/spring-boot-data-cache-jetcache) - 展示 Spring Boot + J2Cache 实现分布式二级缓存。
