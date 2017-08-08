package org.zp.notes.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zp.notes.spring.web.controller.base.BaseController;
import org.zp.notes.spring.web.controller.base.ResponseDTO;

/**
 * service controller 的第一个程序
 * @author Zhang Peng
 */
@Controller
@RequestMapping(value = "/hello")
public class HelloController extends BaseController {

    /**
     * <p>
     * 在本例中，Spring将会将数据传给 hello.jsp
     * </p>
     * 访问形式：http://localhost:8080/hello?name=张三
     */
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public ModelAndView hello(@RequestParam("name") String name) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "你好，" + name);
        mav.setViewName("hello");
        return mav;
    }

    /**
     * <p>
     * 测试 logback 分级日志。配置项见src/main/resouces/logback.xml
     * <p>
     * 访问形式：http://localhost:8080/log
     */
    @ResponseBody
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public ResponseDTO log() {
        String msg = "print log, current level: {}";
        logger.trace(msg, "trace");
        logger.debug(msg, "debug");
        logger.info(msg, "info");
        logger.warn(msg, "warn");
        logger.error(msg, "error");
        return returnWithSuccess(msg, null);
    }
}
