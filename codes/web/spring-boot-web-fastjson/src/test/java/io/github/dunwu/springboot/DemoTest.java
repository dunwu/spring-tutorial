package io.github.dunwu.springboot;

import cn.hutool.core.util.EscapeUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-20
 */
public class DemoTest {

    final String text = "\"{\"account_register_time_level\":232221.0,\"CUSTOMER_NO\":\"1000000009271187\",\"LN_DELQ_NOW_AMT_SUM_LEVEL\":123123,\"TIME\":\"2020-03-12 12:13:14\",\"QRY_TIMES_LN_L1ST_LEVEL\":111111,\"applyAmt\":1000004,\"riskRating0\":[\"A2\",\"\",\"\",\"\",\"C2\",\"D1\",\"E7\"]}\"";
    @Test
    public void test() {
        String unescape = EscapeUtil.unescape(text);
        JSONObject jsonObject = JSONObject.parseObject(text, Feature.AutoCloseSource);
        System.out.println(jsonObject);

    }

}
