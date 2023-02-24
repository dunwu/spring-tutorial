package example.spring.web.websocket.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/ws").addInterceptors(new HandShake());
        registry.addHandler(myHandler(), "/ws/sockjs").addInterceptors(new HandShake()).withSockJS();
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }

}
