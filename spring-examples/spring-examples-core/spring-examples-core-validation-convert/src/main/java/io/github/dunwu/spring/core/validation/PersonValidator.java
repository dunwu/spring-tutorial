package io.github.dunwu.spring.core.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

	private final PersonValidatorConfig personValidatorConfig;

	public PersonValidator(PersonValidatorConfig personValidatorConfig) {
		this.personValidatorConfig = personValidatorConfig;
	}

	/**
	 * 本校验器只针对 Person 校验
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");

		List<Field> fileds = getFields(target.getClass());
		for (Field field : fileds) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().getAnnotation(Valid.class) != null) {
					try {
						ValidatorRule validatorRule = personValidatorConfig.findRule(annotation);
						if (validatorRule != null) {
							validatorRule.valid(annotation, target, field, errors);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private List<Field> getFields(Class<? extends Object> clazz) {
		// 声明Field数组
		List<Field> fields = new ArrayList<>();
		// 如果class类型不为空
		while (clazz != null) {
			// 添加属性到属性数组
			Collections.addAll(fields, clazz.getDeclaredFields());
			clazz = clazz.getSuperclass();
		}
		return fields;
	}

}
