package example.spring.web.websocket.echo;

import example.spring.web.websocket.WebSocketApplication;
import example.spring.web.websocket.client.GreetingService;
import example.spring.web.websocket.client.SimpleClientWebSocketHandler;
import example.spring.web.websocket.client.SimpleGreetingService;
import example.spring.web.websocket.echo.CustomContainerTests.CustomContainerConfiguration;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { WebSocketApplication.class, CustomContainerConfiguration.class },
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomContainerTests {

    private static Logger logger = LoggerFactory.getLogger(CustomContainerTests.class);

    @LocalServerPort
    private int port;

    @Test
    public void echoEndpoint() {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ClientConfiguration.class,
            PropertyPlaceholderAutoConfiguration.class)
            .properties("websocket.uri:ws://localhost:" + this.port + "/ws/echo/websocket")
            .run("--spring.main.web-application-type=none");
        long count = context.getBean(ClientConfiguration.class).latch.getCount();
        AtomicReference<String> messagePayloadReference = context.getBean(ClientConfiguration.class).messagePayload;
        context.close();
        assertThat(count).isEqualTo(0);
        assertThat(messagePayloadReference.get()).isEqualTo("Did you say \"Hello world!\"?");
    }

    @Test
    public void reverseEndpoint() {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ClientConfiguration.class,
            PropertyPlaceholderAutoConfiguration.class)
            .properties("websocket.uri:ws://localhost:" + this.port + "/ws/reverse")
            .run("--spring.main.web-application-type=none");
        long count = context.getBean(ClientConfiguration.class).latch.getCount();
        AtomicReference<String> messagePayloadReference = context.getBean(ClientConfiguration.class).messagePayload;
        context.close();
        assertThat(count).isEqualTo(0);
        assertThat(messagePayloadReference.get()).isEqualTo("Reversed: !dlrow olleH");
    }

    @Configuration
    protected static class CustomContainerConfiguration {

        @Bean
        public ServletWebServerFactory webServerFactory() {
            return new TomcatServletWebServerFactory("/ws", 0);
        }

    }

    @Configuration
    static class ClientConfiguration implements CommandLineRunner {

        private final CountDownLatch latch = new CountDownLatch(1);

        private final AtomicReference<String> messagePayload = new AtomicReference<>();

        @Value("${websocket.uri}")
        private String webSocketUri;

        @Override
        public void run(String... args) throws Exception {
            logger.info("Waiting for response: latch=" + this.latch.getCount());
            if (this.latch.await(10, TimeUnit.SECONDS)) {
                logger.info("Got response: " + this.messagePayload.get());
            } else {
                logger.info("Response not received: latch=" + this.latch.getCount());
            }
        }

        @Bean
        public WebSocketConnectionManager wsConnectionManager() {

            WebSocketConnectionManager manager = new WebSocketConnectionManager(client(), handler(), this.webSocketUri);
            manager.setAutoStartup(true);

            return manager;
        }

        @Bean
        public StandardWebSocketClient client() {
            return new StandardWebSocketClient();
        }

        @Bean
        public SimpleClientWebSocketHandler handler() {
            return new SimpleClientWebSocketHandler(greetingService(), this.latch, this.messagePayload);
        }

        @Bean
        public GreetingService greetingService() {
            return new SimpleGreetingService();
        }

    }

}
