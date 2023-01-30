package example.spring.core.resource;

import org.springframework.util.PathMatcher;

import java.util.Comparator;
import java.util.Map;

/**
 * Java 文件路径匹配
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-12-26
 */
public class JavaFilePathMatcher implements PathMatcher {

    @Override
    public boolean isPattern(String path) {
        return path.endsWith(".java");
    }

    @Override
    public boolean match(String pattern, String path) {
        return path.endsWith(".java");
    }

    @Override
    public boolean matchStart(String pattern, String path) {
        return false;
    }

    @Override
    public String extractPathWithinPattern(String pattern, String path) {
        return null;
    }

    @Override
    public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
        return null;
    }

    @Override
    public Comparator<String> getPatternComparator(String path) {
        return null;
    }

    @Override
    public String combine(String pattern1, String pattern2) {
        return null;
    }

}
