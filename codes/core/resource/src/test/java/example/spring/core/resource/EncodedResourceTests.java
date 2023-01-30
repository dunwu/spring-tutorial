package example.spring.core.resource;

import cn.hutool.core.io.IoUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

/**
 * {@link EncodedResource} 测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-12-26
 */
public class EncodedResourceTests {

    /**
     * 带有字符编码的 {@link FileSystemResource} 示例
     *
     * @see FileSystemResource
     * @see EncodedResource
     */
    @Test
    @DisplayName("带有字符编码的 FileSystemResource 示例")
    public void testFileSystemResource() throws IOException {
        String currentJavaFilePath = System.getProperty("user.dir")
            + "/src/main/java/io/github/dunwu/spring/core/resource/ResourceUtil.java";
        File currentJavaFile = new File(currentJavaFilePath);
        // FileSystemResource => WritableResource => Resource
        FileSystemResource fileSystemResource = new FileSystemResource(currentJavaFilePath);
        EncodedResource encodedResource = new EncodedResource(fileSystemResource, "UTF-8");
        // 字符输入流
        try (Reader reader = encodedResource.getReader()) {
            System.out.println(IoUtil.read(reader));
        }
    }

    /**
     * 带有字符编码的 {@link FileSystemResourceLoader} 示例
     *
     * @see FileSystemResourceLoader
     * @see FileSystemResource
     * @see EncodedResource
     */
    @Test
    @DisplayName("带有字符编码的 FileSystemResourceLoader 示例")
    public void testFileSystemResourceLoader() throws IOException {
        String currentJavaFilePath = "/"
            + System.getProperty("user.dir")
            + "/src/main/java/io/github/dunwu/spring/core/resource/ResourceUtil.java";
        // 新建一个 FileSystemResourceLoader 对象
        FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
        // FileSystemResource => WritableResource => Resource
        Resource resource = resourceLoader.getResource(currentJavaFilePath);
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        // 字符输入流
        try (Reader reader = encodedResource.getReader()) {
            System.out.println(IoUtil.read(reader));
        }
    }

}
