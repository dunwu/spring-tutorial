package example.spring.web.connections;

import example.spring.web.connections.entity.Weather;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("发送 HTTP 消息测试集")
@SpringBootTest
public class SendHttpMessageTests {

    @Autowired
    private WeatherService weatherService;

    @Test
    public void test() {
        Weather weather = weatherService.getWeather("101190101");
        Assertions.assertThat(weather).isNotNull();
        weatherService.printBasicWeatherInfo(weather);
    }

}
