package io.github.dunwu.springboot.logging.web;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @ResponseBody
    @RequestMapping(value = { "/", "/hello" }, method = RequestMethod.GET)
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

}
