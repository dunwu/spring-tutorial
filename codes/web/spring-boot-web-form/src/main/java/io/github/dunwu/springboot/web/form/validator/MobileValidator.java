package io.github.dunwu.springboot.web.form.validator;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.springboot.web.form.validator.annotation.Mobile;
import io.github.dunwu.springboot.web.form.validator.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile isMobile) { }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isBlank(s)) {
            return true;
        } else {
            return ValidatorUtil.isMobile(s);
        }
    }

}
