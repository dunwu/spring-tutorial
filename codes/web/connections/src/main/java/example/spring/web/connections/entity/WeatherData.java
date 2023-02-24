package example.spring.web.connections.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    private String shidu;

    private int pm25;

    private int pm10;

    private String quality;

    private String wendu;

    private String ganmao;

    private List<CoreWeatherInfo> forecast;

    private CoreWeatherInfo yesterday;

}
