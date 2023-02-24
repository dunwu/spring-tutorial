package example.spring.web.multipart;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 */
public class FileSystemStorageServiceImplTests {

    private StorageProperties properties = new StorageProperties();

    private FileSystemStorageServiceImpl service;

    @BeforeEach
    public void init() {
        properties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
        service = new FileSystemStorageServiceImpl(properties);
        service.init();
    }

    @Test
    public void loadNonExistent() {
        assertThat(service.load("foo.txt")).doesNotExist();
    }

    @Test
    public void saveAndLoad() {
        service.store(new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()));
        assertThat(service.load("foo.txt")).exists();
    }

    @Test
    public void saveNotPermitted() {
        try {
            service.store(
                new MockMultipartFile("foo", "../foo.txt", MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()));
        } catch (Exception e) {
            Assertions.assertThat(e instanceof StorageException).isTrue();
        }
    }

    @Test
    public void savePermitted() {
        service.store(
            new MockMultipartFile("foo", "bar/../foo.txt", MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()));
    }

}
