package example.spring.web.connections;

import example.spring.web.connections.entity.Weather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 同时启动HTTP和HTTPS服务
 * <p>
 * 启动应用后，可访问链接：
 * <ul>
 * <li><a href="http://localhost:8080/">http://localhost:8080/</a></li>
 * <li><a href="https://localhost:8443/">https://localhost:8443/</a></li>
 * </ul>
 */
@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class WebMultiConnectorsApplication {

    private final WeatherService weatherService;

    public static void main(String[] args) {
        SpringApplication.run(WebMultiConnectorsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            log.info("查询南京市天气：");
            Weather weather = weatherService.getWeather("101190101");
            if (weather == null) {
                log.info("未查到数据！");
                return;
            }
            weatherService.printBasicWeatherInfo(weather);
        };
    }

}
