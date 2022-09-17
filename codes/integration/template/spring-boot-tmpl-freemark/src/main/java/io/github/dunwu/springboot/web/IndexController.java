package io.github.dunwu.springboot.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;

/**
 * @author Zhang Peng
 * @since 2018-12-19
 */
@Controller
public class IndexController {

    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @GetMapping("/exception")
    public String error(Map<String, Object> model) {
        model.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.put("info", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return "spring/exception";
    }

    @GetMapping(value = { "/", "index" })
    public String welcome(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", this.message);
        return "spring/index";
    }

}
