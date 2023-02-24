package example.spring.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for demo application.
 *
 * @author Dave Syer
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SampleWebUiApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testCreate() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("text", "FOO text");
        map.set("summary", "FOO");
        URI location = this.restTemplate.postForLocation("/", map);
        assertThat(location.toString()).contains("localhost:" + this.port);
    }

    @Test
    public void testCss() {
        ResponseEntity<String> entity = this.restTemplate
            .getForEntity("http://localhost:" + this.port + "/css/bootstrap.min.css", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("body");
    }

    @Test
    public void testHome() {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("<title>Messages");
        assertThat(entity.getBody()).doesNotContain("layout:fragment");
    }

}
