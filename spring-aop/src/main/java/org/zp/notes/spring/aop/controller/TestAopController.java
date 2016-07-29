package org.zp.notes.spring.aop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.zp.notes.spring.aop.sample.Performer;

@Controller
@RequestMapping(value = "/aop")
public class TestAopController {
    
    @Autowired
    Performer performer;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test() {
        performer.perform();
        return null;
    }
}
