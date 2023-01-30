package example.spring.core.resource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * PathMatchingResourcePatternResolver 测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-12-26
 */
public class PathMatchingResourcePatternResolverTests {

    @Test
    public void test() throws IOException {
        // 读取当前 package 对应的所有的 .java 文件
        // *.java
        String currentPackagePath = "/"
            + System.getProperty("user.dir")
            + "/src/main/java/io/github/dunwu/spring/core/resource/";
        String locationPattern = currentPackagePath + "*.java";
        PathMatchingResourcePatternResolver resourcePatternResolver =
            new PathMatchingResourcePatternResolver(new FileSystemResourceLoader());

        resourcePatternResolver.setPathMatcher(new JavaFilePathMatcher());

        Resource[] resources = resourcePatternResolver.getResources(locationPattern);

        Assertions.assertThat(resources).isNotEmpty();

        Stream.of(resources).map(ResourceUtil::getContent).forEach(System.out::println);
    }

}
