package example.spring.web.mvc.validation;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class ValidationController {

    @ResponseBody
    @RequestMapping("/validate")
    public String validate(@Valid JavaBean bean, BindingResult result) {
        if (result.hasErrors()) {
            return "Object has validation errors";
        } else {
            return "No errors";
        }
    }

}
