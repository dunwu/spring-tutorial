package io.github.dunwu.springboot.logging;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(value = { OutputCaptureExtension.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AopLogTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAopLog(CapturedOutput output) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Tom");
        String response = restTemplate.getForObject("/hello?name={name}", String.class, map);
        System.out.println("Response: " + response);
        Assertions.assertEquals("Hello Tom", response);

        // this.output.expect(containsString("HTTP_METHOD : GET"));
        // this.output.expect(containsString("CLASS_METHOD :
        // io.github.dunwu.springboot.web.HelloController.hello"));
        // this.output.expect(containsString("ARGS : [Tom]"));
        // this.output.expect(containsString("RESPONSE : Hello Tom"));
    }

}
