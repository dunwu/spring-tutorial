package io.github.dunwu.springboot.web.constant;

/**
 * @author Zhang Peng
 * @since 2018-12-29
 */
public enum StatusEnum {

    SUCCESS("200", "成功"),
    APP_ERROR("500", "系统内部错误"),
    OTHER_ERROR("501", "其他错误");

    private final String code;

    private final String message;

    StatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
