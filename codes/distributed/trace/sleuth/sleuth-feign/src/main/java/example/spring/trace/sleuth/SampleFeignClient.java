package example.spring.trace.sleuth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sleuth-web", url = "http://127.0.0.1:8080")
public interface SampleFeignClient {

    @GetMapping("/hello")
    String hello(@RequestParam String name);

}
