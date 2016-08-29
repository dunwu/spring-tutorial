package org.zp.notes.spring.helloworld.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/hello")
public class HelloController {
    private Logger log = LoggerFactory.getLogger(HelloController.class);
    
    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public ModelAndView say() {
        ModelAndView mav = new ModelAndView();
        log.info("你好，世界");
        mav.addObject("message", "你好，世界");
        mav.setViewName("hello");
        return mav;
    }
}
