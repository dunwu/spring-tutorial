package example.spring.web.client;

import example.spring.web.client.entity.Weather;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@DisplayName("发送 HTTP 消息测试集")
@SpringBootTest(classes = WebClientApplication.class)
public class WebClientApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestTemplate complexRestTemplate;

    @Test
    @DisplayName("请求方式一")
    public void test() {
        Weather weather =
            restTemplate.getForObject("http://t.weather.sojson.com/api/weather/city/" + 101190101, Weather.class);
        Assertions.assertThat(weather).isNotNull();
        log.info("时间：{}", weather.getTime());
        log.info("空气质量：{}", weather.getData().getQuality());
        log.info("湿度：{}", weather.getData().getShidu());
        log.info("PM25数值：{}", weather.getData().getPm25());
        log.info("温度：{}", weather.getData().getWendu());
        log.info("建议：{}", weather.getData().getGanmao());
    }

    @Test
    @DisplayName("请求方式二")
    public void test2() {
        URI uri = UriComponentsBuilder
            .fromUriString("http://t.weather.sojson.com/api/weather/city/{id}")
            .build(101190101);
        ResponseEntity<Weather> response = complexRestTemplate.getForEntity(uri, Weather.class);
        log.info("Response Status: {}, Response Headers: {}", response.getStatusCode(), response.getHeaders());
        log.info("Weather: {}", response.getBody());
        Weather weather = response.getBody();
        Assertions.assertThat(weather).isNotNull();
        log.info("时间：{}", weather.getTime());
        log.info("空气质量：{}", weather.getData().getQuality());
        log.info("湿度：{}", weather.getData().getShidu());
        log.info("PM25数值：{}", weather.getData().getPm25());
        log.info("温度：{}", weather.getData().getWendu());
        log.info("建议：{}", weather.getData().getGanmao());
    }

}
