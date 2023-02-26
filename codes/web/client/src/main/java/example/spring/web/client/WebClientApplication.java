package example.spring.web.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class WebClientApplication {

    @Bean
    @Primary
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory requestFactory() {
        PoolingHttpClientConnectionManager connectionManager =
            new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(20);

        CloseableHttpClient httpClient = HttpClients.custom()
                                                    .setConnectionManager(connectionManager)
                                                    .evictIdleConnections(30, TimeUnit.SECONDS)
                                                    .disableAutomaticRetries()
                                                    // 有 Keep-Alive 认里面的值，没有的话永久有效
                                                    //.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                                                    // 换成自定义的
                                                    .setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
                                                    .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public RestTemplate complexRestTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofMillis(100))
                      .setReadTimeout(Duration.ofMillis(500))
                      .requestFactory(this::requestFactory)
                      .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(WebClientApplication.class);
    }

}
