package example.spring.web.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Zhang Peng
 * @since 2017/4/12.
 */
@Controller
public class HomeController {

    private static final AtomicInteger number = new AtomicInteger(0);

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>
     * 返回 ModelAndView 对象到视图层。在本例中，视图解析器解析视图名为 index，会自动关联到 index.jsp。
     * <p>
     * 访问形式：http://localhost:8080/
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcome() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("welcome");
        mav.addObject("time", new Date());
        return mav;
    }

    /**
     * <p>
     * 在本例中，Spring将会将数据传给 hello.jsp
     * <p>
     * 访问形式：http://localhost:8080/hello?name=张三
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
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
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public ModelAndView log(HttpServletRequest request) {

        number.getAndIncrement();
        String message = String.format("%s 被访问 %d 次", request.getRequestURL(), number.get());
        log.trace(message, "trace");
        log.debug(message, "debug");
        log.info(message, "info");
        log.warn(message, "warn");
        log.error(message, "error");

        ModelAndView mav = new ModelAndView();
        mav.addObject("message", message);
        mav.setViewName("log");
        return mav;
    }

}
