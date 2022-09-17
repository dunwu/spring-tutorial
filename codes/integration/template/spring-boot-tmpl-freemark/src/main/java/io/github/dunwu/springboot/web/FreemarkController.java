package io.github.dunwu.springboot.web;

import freemarker.template.TemplateException;
import io.github.dunwu.springboot.FreemarkHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhang Peng
 * @since 2018-12-19
 */
@Controller
public class FreemarkController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @Autowired
    private FreemarkHelper freemarkHelper;

    @GetMapping("/exception2")
    public String error2() throws Exception {
        throw new Exception("自定义异常");
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleMyRuntimeException(Exception exception) {
        Map params = new HashMap();
        params.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        params.put("info", exception.getMessage());

        String mergeContent = null;
        try {
            mergeContent = freemarkHelper.getMergeContent("web/exception.ftl", params);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return mergeContent;
    }

    @GetMapping("/index2")
    @ResponseBody
    public String print() throws IOException, TemplateException {
        Map params = new HashMap();
        params.put("time", new Date());
        params.put("message", this.message);
        String mergeContent = freemarkHelper.getMergeContent("web/index.ftl", params);

        log.info("freemark 模板合并后内容：\n{}", mergeContent);
        return mergeContent;
    }

}
