package example.spring.trace.sleuth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * 使用注释内容替代当前相应内容，可以发送真实 HTTP 请求
 */
// @SpringBootTest
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SleuthSampleApplicationTests {

    // public static final String HOST = "http://localhost:8080";
    // private final RestTemplate restTemplate = new RestTemplate();

    public static final String HOST = "";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test01() {
        ResponseEntity<String> entity = restTemplate.getForEntity(HOST + "/", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo("hello, world");
    }

    @Test
    public void test02() {
        Map<String, String> params = new HashMap<>(1);
        params.put("name", "dunwu");
        ResponseEntity<String> entity = restTemplate.getForEntity(HOST + "/hello?name={name}", String.class, params);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo("hello, dunwu");
    }

    @Test
    public void test03() {
        ResponseEntity<String> entity = restTemplate.getForEntity(HOST + "/async", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo("async done");
    }

    @Test
    public void test04() {
        ResponseEntity<String> entity = restTemplate.getForEntity(HOST + "/traced", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test05() {
        ResponseEntity<String> entity = restTemplate.getForEntity(HOST + "/traced", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("traced/");
    }

    @Test
    public void test06() {
        ResponseEntity<String> entity = restTemplate.getForEntity(HOST + "/start", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("start/");
    }

}
