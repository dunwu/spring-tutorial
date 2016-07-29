package org.zp.notes.spring.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/hello")
public class HelloController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public ModelAndView say() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "Hello World! First program!");
        log.debug("say Hello World");
        mav.setViewName("hello"); // 设置视图名称
        return mav;
    }
}
