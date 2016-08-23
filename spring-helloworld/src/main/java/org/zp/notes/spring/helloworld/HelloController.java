package org.zp.notes.spring.helloworld;

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
        mav.addObject("message", "Hello World! First program!");
        mav.setViewName("hello");
        return mav;
    }
}
