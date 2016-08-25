package org.zp.notes.spring.helloworld.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/hello")
public class HelloController {
    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public ModelAndView say() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "你好，世界");
        mav.setViewName("hello");
        return mav;
    }
}
