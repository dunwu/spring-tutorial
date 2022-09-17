package io.github.dunwu.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping({ "/", "/hello" })
    public String helloWorld() {
        StringBuilder sb = new StringBuilder();
        sb.append("本服务同时支持 HTTP 和 HTTPS 服务。");
        sb.append("请尝试访问 http://localhost:8080/ 和 https://localhost:8443/");
        return sb.toString();
    }

}
