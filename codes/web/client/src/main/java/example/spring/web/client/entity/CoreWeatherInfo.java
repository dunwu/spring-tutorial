package example.spring.web.client.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoreWeatherInfo {

    private String date;

    private String high;

    private String low;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ymd;

    private String week;

    private String sunrise;

    private String sunset;

    private int aqi;

    private String fx;

    private String fl;

    private String type;

    private String notice;

}
