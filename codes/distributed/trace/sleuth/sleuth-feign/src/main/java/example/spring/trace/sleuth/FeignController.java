package example.spring.trace.sleuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    private SampleFeignClient feignClient;

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return feignClient.hello(name);
    }

}
