package example.spring.core.conversion;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-14
 */
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String dateString) {
        if (StrUtil.isBlank(dateString)) {
            return null;
        }

        return DateUtil.parse(dateString, DatePattern.NORM_DATETIME_PATTERN);
    }

}
