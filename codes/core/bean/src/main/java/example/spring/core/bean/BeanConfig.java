package example.spring.core.bean;

import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-10-02
 */
@Configuration
public class BeanConfig {

    @Bean
    public DozerBeanMapperFactoryBean dozerFactory(ResourcePatternResolver resourcePatternResolver)
        throws IOException {
        DozerBeanMapperFactoryBean factoryBean = new DozerBeanMapperFactoryBean();
        factoryBean.setMappingFiles(
            resourcePatternResolver.getResources("classpath*:/*mapping.xml"));
        return factoryBean;
    }

    @Bean
    public Mapper dozerMapper(DozerBeanMapperFactoryBean dozerFactory) {
        return dozerFactory.getObject();
    }

}
