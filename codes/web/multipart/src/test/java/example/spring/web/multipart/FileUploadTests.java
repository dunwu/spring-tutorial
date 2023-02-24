package example.spring.web.multipart;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StorageService storageService;

    @SuppressWarnings("unchecked")
    @Test
    public void should404WhenMissingFile() throws Exception {
        given(this.storageService.loadAsResource("test.txt")).willThrow(StorageFileNotFoundException.class);

        this.mvc.perform(get("/files/test.txt")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldListAllFiles() throws Exception {
        given(this.storageService.loadAll()).willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

        this.mvc.perform(get("/")).andExpect(status().isOk()).andExpect(model().attribute("files",
            Matchers.contains("http://localhost/files/first.txt", "http://localhost/files/second.txt")));
    }

    @Test
    public void shouldSaveUploadedFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain",
            "Spring Framework".getBytes());
        this.mvc.perform(fileUpload("/").file(multipartFile)).andExpect(status().isFound())
                .andExpect(header().string("Location", "/"));

        then(this.storageService).should().store(multipartFile);
    }

}
