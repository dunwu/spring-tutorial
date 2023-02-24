package example.spring.web.websocket;

import example.spring.web.websocket.client.GreetingService;
import example.spring.web.websocket.client.SimpleGreetingService;
import example.spring.web.websocket.echo.DefaultEchoService;
import example.spring.web.websocket.echo.EchoService;
import example.spring.web.websocket.echo.EchoWebSocketHandler;
import example.spring.web.websocket.reverse.ReverseWebSocketEndpoint;
import example.spring.web.websocket.snake.SnakeWebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class WebSocketApplication extends SpringBootServletInitializer implements WebSocketConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(echoWebSocketHandler(), "/echo").withSockJS();
        registry.addHandler(snakeWebSocketHandler(), "/snake").withSockJS();
    }

    @Bean
    public WebSocketHandler echoWebSocketHandler() {
        return new EchoWebSocketHandler(echoService());
    }

    @Bean
    public EchoService echoService() {
        return new DefaultEchoService("Did you say \"%s\"?");
    }

    @Bean
    public WebSocketHandler snakeWebSocketHandler() {
        return new PerConnectionWebSocketHandler(SnakeWebSocketHandler.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebSocketApplication.class);
    }

    @Bean
    public GreetingService greetingService() {
        return new SimpleGreetingService();
    }

    @Bean
    public ReverseWebSocketEndpoint reverseWebSocketEndpoint() {
        return new ReverseWebSocketEndpoint();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
