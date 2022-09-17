package io.github.dunwu.springboot;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AopLogTests {

    @Rule
    public OutputCapture output = new OutputCapture();

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAopLog() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Tom");
        String response = restTemplate.getForObject("/hello?name={name}", String.class, map);
        System.out.println("Response: " + response);
        Assert.assertEquals("Hello Tom", response);

        // this.output.expect(containsString("HTTP_METHOD : GET"));
        // this.output.expect(containsString("CLASS_METHOD :
        // io.github.dunwu.springboot.web.HelloController.hello"));
        // this.output.expect(containsString("ARGS : [Tom]"));
        // this.output.expect(containsString("RESPONSE : Hello Tom"));
    }

}
