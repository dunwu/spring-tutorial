package example.spring.web.connections;

import example.spring.web.connections.entity.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    private final WeatherService weatherService;

    public HelloController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping("hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("weather")
    public Weather weather() {
        log.info("查询南京市天气：");
        Weather weather = weatherService.getWeather("101190101");
        if (weather == null) {
            log.info("未查到数据！");
            return null;
        }
        weatherService.printBasicWeatherInfo(weather);
        return weather;
    }

}
