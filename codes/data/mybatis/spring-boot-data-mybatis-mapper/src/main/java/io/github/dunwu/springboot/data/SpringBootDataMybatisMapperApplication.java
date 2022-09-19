package io.github.dunwu.springboot.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tk.mybatis.spring.annotation.MapperScan;

@Slf4j
@Controller
@EnableWebMvc
@SpringBootApplication
@MapperScan(basePackages = "io.github.dunwu.springboot.data.mapper")
public class SpringBootDataMybatisMapperApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataMybatisMapperApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("服务启动完成!");
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:countries";
    }

}
