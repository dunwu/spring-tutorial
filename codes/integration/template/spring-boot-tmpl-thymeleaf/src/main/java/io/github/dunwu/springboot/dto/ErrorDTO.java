package io.github.dunwu.springboot.dto;

public class ErrorDTO<T> {

    public static final Integer OK = 0;

    public static final Integer ERROR = 100;

    private Integer code;

    private String message;

    private String url;

    private T data;

    public static Integer getERROR() {
        return ERROR;
    }

    public static Integer getOK() {
        return OK;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
