package io.github.dunwu.springboot.security;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 应用启动类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-14
 */
@EnableAsync
@SpringBootApplication(scanBasePackages = "io.github.dunwu")
// @MapperScan("io.github.dunwu.admin.*.mapper")
public class ShiroApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ShiroApplication.class).run(args);
    }

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }

}
