package io.github.dunwu.springboot.web;

import io.github.dunwu.springboot.web.entity.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebHttpsApplication {

    private static final Logger log = LoggerFactory.getLogger(WebHttpsApplication.class);

    private final WeatherService weatherService;

    public WebHttpsApplication(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

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
