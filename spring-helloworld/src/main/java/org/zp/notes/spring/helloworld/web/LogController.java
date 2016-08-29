package org.zp.notes.spring.helloworld.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/log")
public class LogController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public String print() {
        String msg = "尝试打印日志";
        log.trace(msg);
        log.debug(msg);
        log.info(msg);
        log.warn(msg);
        log.error(msg);
        return msg;
    }
}
