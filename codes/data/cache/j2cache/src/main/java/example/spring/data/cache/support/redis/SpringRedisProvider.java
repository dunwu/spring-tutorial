package example.spring.data.cache.support.redis;

import example.spring.data.cache.config.J2CacheProperties;
import example.spring.data.cache.support.util.SpringUtil;
import net.oschina.j2cache.Cache;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheExpiredListener;
import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.CacheProvider;
import net.oschina.j2cache.NullCache;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring redis缓存
 * @author zhangsaizz
 */
public class SpringRedisProvider implements CacheProvider {

    protected ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>();

    private RedisTemplate<String, Serializable> redisTemplate;

    private J2CacheProperties config;

    private String namespace;

    private String storage;

    @Override
    public String name() {
        return "redis";
    }

    @Override
    public int level() {
        return CacheObject.LEVEL_2;
    }

    @Override
    public Cache buildCache(String region, CacheExpiredListener listener) {
        if (config.isL2CacheOpen() == false) {
            return new NullCache();
        }
        Cache cache = caches.get(region);
        if (cache == null) {
            synchronized (SpringRedisProvider.class) {
                cache = caches.get(region);
                if (cache == null) {
                    if ("hash".equalsIgnoreCase(this.storage)) {
                        cache = new SpringRedisCache(this.namespace, region, redisTemplate);
                    } else {
                        cache = new SpringRedisGenericCache(this.namespace, region, redisTemplate);
                    }
                    caches.put(region, cache);
                }
            }
        }
        return cache;
    }

    @Override
    public Cache buildCache(String region, long timeToLiveInSeconds, CacheExpiredListener listener) {
        return buildCache(region, listener);
    }

    @Override
    public Collection<CacheChannel.Region> regions() {
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start(Properties props) {
        this.namespace = props.getProperty("namespace");
        this.storage = props.getProperty("storage");
        this.config = SpringUtil.getBean(J2CacheProperties.class);
        if (config.isL2CacheOpen() == false) {
            return;
        }
        this.redisTemplate = SpringUtil.getBean("j2CacheRedisTemplate", RedisTemplate.class);
    }

    @Override
    public void stop() {
        // 由spring控制
    }

}
