package example.spring.web.controller;

import io.github.dunwu.tool.data.DataResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zhang Peng
 * @since 2017/4/12.
 */
@RestController
public class IndexController {

    /**
     * <p>
     * 在本例中，Spring将会将数据传给 hello.jsp
     * <p>
     * 访问形式：http://localhost:8080/hello?name=张三
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public DataResult<String> hello(@RequestParam("name") String name) {
        return DataResult.ok("你好，" + name);
    }

}
