package io.github.dunwu.spring.core.convert;

import io.github.dunwu.util.time.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-14
 */
public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String dateString) {
		if (StringUtils.isBlank(dateString)) {
			return null;
		}

		Date date = null;
		try {
			date = DateFormatUtil.parseDate(DateFormatUtil.DatePattern.PATTERN_DEFAULT_ON_SECOND, dateString);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

}
