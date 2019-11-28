# spring-tutorial-integration-cache

## 项目说明

请执行以下三个单元测试例，来观察在 Spring 中如何使用缓存。

- `io.github.dunwu.spring.cache.SpringCaffeineCacheTest`
- `io.github.dunwu.spring.cache.SpringConcurrentHashMapCacheTest`
- `io.github.dunwu.spring.cache.SpringEhcacheCacheTest`

## 要点

在 xml 中配置缓存的存储类型，并开启 `CacheManager`

然后，使用 Spring 内置的关于缓存的注解，注意在示例中体会用法。


### @Cacheable

表明所修饰的方法是可以缓存的：当第一次调用这个方法时，它的结果会被缓存下来，在缓存的有效时间内，以后访问这个方法都直接返回缓存结果，不再执行方法中的代码段。
这个注解可以用`condition`属性来设置条件，如果不满足条件，就不使用缓存能力，直接执行方法。
可以使用`key`属性来指定 key 的生成规则。

### @CachePut

与`@Cacheable`不同，`@CachePut`不仅会缓存方法的结果，还会执行方法的代码段。
它支持的属性和用法都与`@Cacheable`一致。

### @CacheEvict

与`@Cacheable`功能相反，`@CacheEvict`表明所修饰的方法是用来删除失效或无用的缓存数据。
下面是`@Cacheable`、`@CacheEvict`和`@CachePut`基本使用方法的一个集中展示：

```java
@Service
public class UserService {
    // @Cacheable可以设置多个缓存，形式如：@Cacheable({"books", "isbns"})
    @Cacheable(value={"users"}, key="#user.id")
    public User findUser(User user) {
        return findUserInDB(user.getId());
    }

    @Cacheable(value = "users", condition = "#user.getId() <= 2")
    public User findUserInLimit(User user) {
        return findUserInDB(user.getId());
    }

    @CachePut(value = "users", key = "#user.getId()")
    public void updateUser(User user) {
        updateUserInDB(user);
    }

    @CacheEvict(value = "users")
    public void removeUser(User user) {
        removeUserInDB(user.getId());
    }

    @CacheEvict(value = "users", allEntries = true)
    public void clear() {
        removeAllInDB();
    }
}
```

### @Caching

如果需要使用同一个缓存注解（`@Cacheable`、`@CacheEvict`或`@CachePut`）多次修饰一个方法，就需要用到`@Caching`。

```java
@Caching(evict = { @CacheEvict("primary"), @CacheEvict(cacheNames="secondary", key="#p0") })
public Book importBooks(String deposit, Date date)
```

### @CacheConfig

与前面的缓存注解不同，这是一个类级别的注解。
如果类的所有操作都是缓存操作，你可以使用`@CacheConfig`来指定类，省去一些配置。

```java
@CacheConfig("books")
public class BookRepositoryImpl implements BookRepository {
	@Cacheable
	public Book findBook(ISBN isbn) {...}
}
```
