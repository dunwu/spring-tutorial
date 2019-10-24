package io.github.dunwu.spring.core.convert;

import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-05
 */
public class SpringConvertTest {

	@Test
	public void testDateConverter() {
		String str = "2019-06-14 12:00:00";
		StringToDateConverter converter = new StringToDateConverter();
		Date date = converter.convert(str);
		Assert.assertNotNull(date);
		System.out.println("Date: " + date);
	}

}
