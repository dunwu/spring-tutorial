package example.spring.core.actuator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "management.server.port:0" })
public class ActuatorPortTests {

    @LocalServerPort
    private int port = 8080;

    @LocalManagementPort
    private int managementPort = 18080;

    @Test
    public void testHealth() {
        ResponseEntity<String> entity = new TestRestTemplate().withBasicAuth("root", "root")
                                                              .getForEntity("http://localhost:"
                                                                  + this.managementPort
                                                                  + "/actuator/health", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("\"status\":\"UP\"");
    }

    @Test
    public void testHome() {
        ResponseEntity<String> entity =
            new TestRestTemplate().getForEntity("http://localhost:" + this.port, String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testMetrics() {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate()
            .getForEntity("http://localhost:" + this.managementPort + "/actuator/metrics", Map.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}
