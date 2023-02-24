package example.spring.web.https;

import example.spring.web.https.entity.Weather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class WebHttpsApplication {

    private final WeatherService weatherService;

    public static void main(String[] args) {
        SpringApplication.run(WebHttpsApplication.class);
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
