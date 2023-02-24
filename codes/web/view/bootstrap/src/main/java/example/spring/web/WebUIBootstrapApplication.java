package example.spring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

@SpringBootApplication
public class WebUIBootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebUIBootstrapApplication.class, args);
    }

    @Bean
    public Converter<String, Message> messageConverter() {
        return new Converter<String, Message>() {
            @Override
            public Message convert(String id) {
                return messageRepository().findMessage(Long.valueOf(id));
            }
        };
    }

    @Bean
    public MessageRepository messageRepository() {
        return new InMemoryMessageRepository();
    }

}
