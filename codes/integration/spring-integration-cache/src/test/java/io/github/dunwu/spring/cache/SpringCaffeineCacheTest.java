package io.github.dunwu.spring.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 使用 Caffeine 作为 Spring 缓存测试
 * <p>
 * 配置内容见：spring/spring-caffeine.xml
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-04
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:spring/spring-caffeine.xml" })
public class SpringCaffeineCacheTest {

    @Autowired
    private CacheTest cacheTest;

    /**
     * 测试当前真实工作的 CacheManager 是什么
     */
    @Test
    public void getCacheManager() {
        cacheTest.getCacheManager();
    }

    /**
     * 测试@Cacheable
     */
    @Test
    public void testFindUser() throws InterruptedException {
        cacheTest.testFindUser();
    }

    /**
     * 测试@Cacheable设置Spring SpEL条件限制
     */
    @Test
    public void testFindUserInLimit() throws InterruptedException {
        cacheTest.testFindUserInLimit();
    }

    /**
     * 测试@CachePut
     */
    @Test
    public void testUpdateUser() {
        cacheTest.testUpdateUser();
    }

    /**
     * 测试@CacheEvict删除指定缓存
     */
    @Test
    public void testRemoveUser() {
        cacheTest.testRemoveUser();
    }

    /**
     * 测试@CacheEvict删除所有缓存
     */
    @Test
    public void testClear() {
        cacheTest.testClear();
    }

}
