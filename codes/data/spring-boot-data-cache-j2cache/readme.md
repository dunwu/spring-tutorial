此为 spring boot2 版本！！
如下即可使用 j2cache 缓存方法

```
@Autowired
private CacheChannel cacheChannel;
```

在 application.properties 中支持指定 j2cache 配置文件，让你开发环境和生产环境分离

```
j2cache.config-location=/j2cache-${spring.profiles.active}.properties
```

如下两项配置在 application.properties,可以开启对 spring cahce 的支持

```
j2cache.open-spring-cache=true
spring.cache.type=GENERIC
```

如下两项配置在 application.properties,可以设置 spring cache 是否缓存 null 值，默认是 true

```
j2cache.allow-null-values=true
```

如下配置在 application.properties,可以选择缓存清除的模式

- 缓存清除模式
- active:主动清除，二级缓存过期主动通知各节点清除，优点在于所有节点可以同时收到缓存清除
- passive:被动清除，一级缓存过期进行通知各节点清除一二级缓存
- blend:两种模式一起运作，对于各个节点缓存准确性以及及时性要求高的可以使用（推荐使用前面两种模式中一种）

```
j2cache.cache-clean-mode=passive
```

在 j2cache.properties 中配置,可以使用 springRedis 进行广播通知缓失效

```
j2cache.broadcast = io.github.dunwu.springboot.data.support.redis.SpringRedisPubSubPolicy
```

在 j2cache.properties 中配置,使用 springRedis 替换二级缓存

```
j2cache.L2.provider_class = io.github.dunwu.springboot.data.support.redis.SpringRedisProvider
j2cache.L2.config_section = redis (如果要使用lettuce客户端请配置为lettuce)
```

在 application.properties 中支持 redis 客户端

- jedis
- lettuce

```
j2cache.redis-client=jedis
```

在 application.properties 中支持关闭二级缓存

```
j2cache.l2-cache-open=false（默认开启）
```
