package io.github.dunwu.springboot;

import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.spring.DozerBeanMapperFactoryBean;
import io.github.dunwu.springboot.bean.UserDO;
import io.github.dunwu.springboot.bean.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringBootBeanApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringBootBeanApplication.class);

    @Autowired
    private BeanUtil beanUtil;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBeanApplication.class, args);
    }

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

    @PostConstruct
    public void postConstruct() {
        UserDO[] userDOS = { new UserDO(1L, "userA", "用户A"), new UserDO(2L, "userB", "用户B"),
            new UserDO(3L, "userC", "用户C"), new UserDO(4L, "userD", "用户D") };
        List<UserDTO> list = beanUtil.mapList(Arrays.asList(userDOS), UserDTO.class);
        log.info("List<UserDO> 转化为 List<UserDTO> 的结果: {}", list);
    }

}
