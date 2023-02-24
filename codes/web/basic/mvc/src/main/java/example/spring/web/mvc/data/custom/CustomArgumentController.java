package example.spring.web.mvc.data.custom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomArgumentController {

    @RequestMapping(value = "/data/custom", method = RequestMethod.GET)
    public @ResponseBody
    String custom(@RequestAttribute("foo") String foo) {
        return "Got 'foo' request attribute value '" + foo + "'";
    }

    @ModelAttribute
    void beforeInvokingHandlerMethod(HttpServletRequest request) {
        request.setAttribute("foo", "bar");
    }

}
