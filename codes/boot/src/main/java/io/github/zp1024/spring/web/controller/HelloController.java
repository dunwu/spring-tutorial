package io.github.zp1024.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Zhang Peng
 * @date 2017/7/13 18:02
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

	/**
	 * <p>访问形式：http://localhost:8443/hello/Victor</p>
	 */
	@ResponseBody
	@GetMapping("/{username}")
	public String helloWorld(@PathVariable("username") String username) {
		return "Hello " + username;
	}
}
