package example.spring.web.mvc.validation;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.util.Date;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class JavaBean {

    @NotNull
    @Max(5)
    private Integer number;

    @NotNull
    @Future
    @DateTimeFormat(iso = ISO.DATE)
    private Date date;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
