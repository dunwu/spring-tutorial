package io.github.dunwu.springboot;

import io.github.dunwu.springboot.bean.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootHttpsApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringBootHttpsApplication.class);

    private final WeatherService weatherService;

    public SpringBootHttpsApplication(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHttpsApplication.class);
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
