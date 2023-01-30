package example.spring.data.cache.config;

import example.spring.data.cache.support.util.SpringJ2CacheConfigUtil;
import example.spring.data.cache.support.util.SpringUtil;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.J2CacheBuilder;
import net.oschina.j2cache.J2CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.StandardEnvironment;

/**
 * 启动入口
 * @author zhangsaizz
 */
@ConditionalOnClass(J2Cache.class)
@EnableConfigurationProperties({ J2CacheProperties.class })
@Configuration
@PropertySource(value = "${j2cache.config-location}", encoding = "UTF-8", ignoreResourceNotFound = true)
public class J2CacheAutoConfiguration {

    @Autowired
    private StandardEnvironment standardEnvironment;

    @Bean
    @DependsOn({ "springUtil", "j2CacheConfig" })
    public CacheChannel cacheChannel(J2CacheConfig j2CacheConfig) {
        J2CacheBuilder builder = J2CacheBuilder.init(j2CacheConfig);
        return builder.getChannel();
    }

    @Bean
    public J2CacheConfig j2CacheConfig() {
        return SpringJ2CacheConfigUtil.initFromConfig(standardEnvironment);
    }

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

}
