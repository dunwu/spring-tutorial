package example.spring.web.https.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    private String message;

    private int status;

    private String date;

    private String time;

    private CityInfo cityInfo;

    private WeatherData data;

}
