package example.spring.web.client;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.util.Arrays;

public class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {

    private final long DEFAULT_SECONDS = 30;

    @Override
    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
        return Arrays.stream(response.getHeaders(HTTP.CONN_KEEP_ALIVE))
                     .filter(h -> StrUtil.equalsIgnoreCase(h.getName(), "timeout") && NumberUtil.isNumber(h.getValue()))
                     .findFirst()
                     .map(h -> NumberUtil.parseLong(h.getValue()))
                     .orElse(DEFAULT_SECONDS) * 1000;
    }

}
