package example.spring.core.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.stream.Stream;
import javax.annotation.PostConstruct;

/**
 * 注入资源测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-12-23
 * @see Resource
 * @see Value
 */
public class InjectingResourceDemo {

    @Value("classpath:/META-INF/dev.properties")
    private Resource devPropertiesResource;

    @Value("classpath*:/META-INF/*.properties")
    private Resource[] propertiesResources;

    @Value("${user.dir}")
    private String currentProjectRootPath;

    @PostConstruct
    public void init() {
        System.out.println(ResourceUtil.getContent(devPropertiesResource));
        System.out.println("================");
        Stream.of(propertiesResources).map(ResourceUtil::getContent).forEach(System.out::println);
        System.out.println("================");
        System.out.println(currentProjectRootPath);
    }

}
