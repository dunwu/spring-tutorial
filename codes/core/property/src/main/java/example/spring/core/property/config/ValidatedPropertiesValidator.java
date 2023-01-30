package example.spring.core.property.config;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * 自定义属性校验器，校验 {@link ValidatedProperties}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see ValidatedProperties
 * @since 2019-11-20
 */
public class ValidatedPropertiesValidator implements Validator {

    private final Pattern pattern = Pattern.compile(
        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    @Override
    public boolean supports(Class<?> type) {
        return type == ValidatedProperties.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "host", "host.empty");
        ValidationUtils.rejectIfEmpty(errors, "port", "port.empty");
        ValidatedProperties properties = (ValidatedProperties) o;

        if (properties.getHost() != null && !this.pattern.matcher(properties.getHost()).matches()) {
            errors.rejectValue("host", "Invalid host");
        }
    }

}
