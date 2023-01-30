package example.spring.data.cache.config;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-14
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "io.github.dunwu.springboot.data.mapper")
public class CacheAutoConfiguration {

}
