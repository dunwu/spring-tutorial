package example.spring.web.websocket.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import javax.servlet.http.HttpSession;

public class HandShake implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
        Map<String, Object> attributes) throws Exception {
        System.out.println("Websocket:用户[ID:"
            + ((ServletServerHttpRequest) request).getServletRequest().getSession(false).getAttribute("uid")
            + "]已经建立连接");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            // 标记用户
            Long uid = (Long) session.getAttribute("uid");
            if (uid != null) {
                attributes.put("uid", uid);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
        Exception exception) {
    }

}
