package org.zp.notes.spring.helloworld.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * spring mvc 的第一个程序
 *
 * @author vicotr zhang
 * @since 2016.07.29
 */
@Controller
public class HelloController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>返回 ModelAndView 对象到视图层。在本例中，视图解析器解析视图名为 index，会自动关联到 index.jsp。
     * <p>访问形式：http://localhost:8080/
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    /**
     * <p>在本例中，Spring将会将数据传给 hello.jsp
     * <p>访问形式：http://localhost:8080/hello?name=张三
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView hello(@RequestParam("name") String name) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "你好，" + name);
        mav.setViewName("hello");
        return mav;
    }

    /**
     * <p>测试 logback 分级日志。配置项见src/main/resouces/logback.xml
     * <p>访问形式：http://localhost:8080/log
     */
    @ResponseBody
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public String log() {
        String msg = "print log, current level: {}";
        log.trace(msg, "trace");
        log.debug(msg, "debug");
        log.info(msg, "info");
        log.warn(msg, "warn");
        log.error(msg, "error");
        return msg;
    }
}
