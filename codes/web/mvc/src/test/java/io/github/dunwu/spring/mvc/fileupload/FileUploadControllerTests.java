package io.github.dunwu.spring.mvc.fileupload;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import io.github.dunwu.spring.mvc.AbstractContextControllerTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class FileUploadControllerTests extends AbstractContextControllerTests {

	@Test
	public void readString() throws Exception {

		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());

		webAppContextSetup(this.wac).build()
				.perform(fileUpload("/fileupload").file(file))
				.andExpect(model().attribute("message", "File 'orig' uploaded successfully"));
	}

}
