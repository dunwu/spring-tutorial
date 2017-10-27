package io.github.dunwu.spring.thirdparty.ehcache;

import org.junit.Test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 测试Cache的操作
 *
 * @author Zhang Peng
 */
public class CacheOperationTest {
    /**
     * 使用Ehcache默认配置(classpath下的ehcache.xml)获取单例的CacheManager实例
     */
    @Test
    public void operation() {
        CacheManager manager = CacheManager.newInstance("src/test/resources/ehcache/ehcache.xml");

        // 获得Cache的引用
        Cache cache = manager.getCache("users");

        // 将一个Element添加到Cache
        cache.put(new Element("key1", "value1"));

        // 获取Element，Element类支持序列化，所以下面两种方法都可以用
        Element element1 = cache.get("key1");
        // 获取非序列化的值
        System.out.println("key=" + element1.getObjectKey() + ", value=" + element1.getObjectValue());
        // 获取序列化的值
        System.out.println("key=" + element1.getKey() + ", value=" + element1.getValue());

        // 更新Cache中的Element
        cache.put(new Element("key1", "value2"));
        Element element2 = cache.get("key1");
        System.out.println("key=" + element2.getObjectKey() + ", value=" + element2.getObjectValue());

        // 获取Cache的元素数
        System.out.println("cache size:" + cache.getSize());

        // 获取MemoryStore的元素数
        System.out.println("MemoryStoreSize:" + cache.getMemoryStoreSize());

        // 获取DiskStore的元素数
        System.out.println("DiskStoreSize:" + cache.getDiskStoreSize());

        // 移除Element
        cache.remove("key1");
        System.out.println("cache size:" + cache.getSize());

        // 关闭当前CacheManager对象
        manager.shutdown();

        // 关闭CacheManager单例实例
        CacheManager.getInstance().shutdown();
    }
}
