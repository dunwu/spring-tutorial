package io.github.dunwu.spring.mvc.fileupload;

import io.github.dunwu.spring.mvc.AbstractContextControllerTests;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class FileUploadControllerTests extends AbstractContextControllerTests {

    @Test
    public void readString() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());

        webAppContextSetup(this.wac).build().perform(fileUpload("/fileupload").file(file))
                                    .andExpect(model().attribute("message", "File 'orig' uploaded successfully"));
    }

}
