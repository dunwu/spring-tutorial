package io.github.dunwu.spring.thirdparty.ehcache;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

/**
 * 测试CacheManager的操作
 *
 * @author Zhang Peng
 */
public class CacheManagerOperationTest {
    private final Logger log = LoggerFactory.getLogger(CacheManagerOperationTest.class);

    /**
     * 使用Ehcache默认配置(classpath下的ehcache.xml)获取单例的CacheManager实例
     */
    @Test
    public void create01() {
        CacheManager.create();

        String[] cacheNames = CacheManager.getInstance().getCacheNames();
        for (String name : cacheNames) {
            System.out.println("name:" + name);
        }
    }

    /**
     * 使用Ehcache默认配置(classpath下的ehcache.xml)新建一个单例的CacheManager实例
     */
    @Test
    public void create02() {
        CacheManager.newInstance();

        String[] cacheNames = CacheManager.getInstance().getCacheNames();
        for (String name : cacheNames) {
            System.out.println("name:" + name);
        }
    }

    /**
     * 使用不同的配置文件分别创建一个CacheManager实例
     */
    @Test
    public void create03() {
        CacheManager manager1 = CacheManager.newInstance("src/test/resources/ehcache/ehcache1.xml");
        CacheManager manager2 = CacheManager.newInstance("src/test/resources/ehcache/ehcache1.xml");
        String[] cacheNamesForManager1 = manager1.getCacheNames();
        String[] cacheNamesForManager2 = manager2.getCacheNames();

        for (String name : cacheNamesForManager1) {
            System.out.println("[ehcache1.xml]name:" + name);
        }

        for (String name : cacheNamesForManager2) {
            System.out.println("[ehcache2.xml]name:" + name);
        }
    }

    /**
     * 基于classpath下的配置文件创建CacheManager实例
     */
    @Test
    public void create04() {
        URL url = getClass().getResource("/cache/ehcache.xml");
        CacheManager manager = CacheManager.newInstance(url);
        String[] cacheNames = manager.getCacheNames();

        for (String name : cacheNames) {
            System.out.println("[ehcache.xml]name:" + name);
        }
    }

    /**
     * 基于IO流得到配置文件，并创建CacheManager实例
     */
    @Test
    public void create05() throws Exception {
        InputStream fis = new FileInputStream(new File("src/test/resources/ehcache/ehcache.xml").getAbsolutePath());
        CacheManager manager = CacheManager.newInstance(fis);
        fis.close();
        String[] cacheNames = manager.getCacheNames();

        for (String name : cacheNames) {
            System.out.println("[ehcache.xml]name:" + name);
        }
    }

    /**
     * 使用默认配置(classpath下的ehcache.xml)添加缓存
     */
    @Test
    public void addAndRemove01() {
        CacheManager singletonManager = CacheManager.create();

        // 添加缓存
        singletonManager.addCache("testCache");

        // 打印配置信息和状态
        Cache test = singletonManager.getCache("testCache");
        System.out.println("cache name:" + test.getCacheConfiguration().getName());
        System.out.println("cache status:" + test.getStatus().toString());
        System.out.println("maxElementsInMemory:" + test.getCacheConfiguration().getMaxElementsInMemory());
        System.out.println("timeToIdleSeconds:" + test.getCacheConfiguration().getTimeToIdleSeconds());
        System.out.println("timeToLiveSeconds:" + test.getCacheConfiguration().getTimeToLiveSeconds());

        // 删除缓存
        singletonManager.removeCache("testCache");
        System.out.println("cache status:" + test.getStatus().toString());
    }

    /**
     * 使用自定义配置添加缓存，注意缓存未添加进CacheManager之前并不可用
     */
    @Test
    public void addAndRemove02() {
        CacheManager singletonManager = CacheManager.create();

        // 添加缓存
        Cache memoryOnlyCache = new Cache("testCache2", 5000, false, false, 5, 2);
        singletonManager.addCache(memoryOnlyCache);

        // 打印配置信息和状态
        Cache test = singletonManager.getCache("testCache2");
        System.out.println("cache name:" + test.getCacheConfiguration().getName());
        System.out.println("cache status:" + test.getStatus().toString());
        System.out.println("maxElementsInMemory:" + test.getCacheConfiguration().getMaxElementsInMemory());
        System.out.println("timeToIdleSeconds:" + test.getCacheConfiguration().getTimeToIdleSeconds());
        System.out.println("timeToLiveSeconds:" + test.getCacheConfiguration().getTimeToLiveSeconds());

        // 删除缓存
        singletonManager.removeCache("testCache2");
        System.out.println("cache status:" + test.getStatus().toString());
    }

    /**
     * 使用特定的配置添加缓存
     */
    @Test
    public void addAndRemove03() {
        CacheManager manager = CacheManager.create();

        // 添加缓存
        Cache testCache = new Cache(new CacheConfiguration("testCache3", 5000)
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU).eternal(false).timeToLiveSeconds(60)
                .timeToIdleSeconds(30).diskExpiryThreadIntervalSeconds(0)
                .persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.LOCALTEMPSWAP)));
        manager.addCache(testCache);

        // 打印配置信息和状态
        Cache test = manager.getCache("testCache3");
        System.out.println("cache name:" + test.getCacheConfiguration().getName());
        System.out.println("cache status:" + test.getStatus().toString());
        System.out.println("maxElementsInMemory:" + test.getCacheConfiguration().getMaxElementsInMemory());
        System.out.println("timeToIdleSeconds:" + test.getCacheConfiguration().getTimeToIdleSeconds());
        System.out.println("timeToLiveSeconds:" + test.getCacheConfiguration().getTimeToLiveSeconds());

        // 删除缓存
        manager.removeCache("testCache3");
        System.out.println("cache status:" + test.getStatus().toString());
    }
}
