package io.github.dunwu.spring.mvc.validation;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class ValidationController {

	// enforcement of constraints on the JavaBean arg require a JSR-303 provider on the
	// classpath

	@RequestMapping("/validate")
	public @ResponseBody
	String validate(@Valid JavaBean bean, BindingResult result) {
		if (result.hasErrors()) {
			return "Object has validation errors";
		} else {
			return "No errors";
		}
	}

}
