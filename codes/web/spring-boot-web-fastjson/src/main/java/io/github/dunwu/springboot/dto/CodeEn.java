package io.github.dunwu.springboot.dto;

/**
 * @author Zhang Peng
 * @since 2018-12-29
 */
public enum CodeEn {

    SUCCESS("200", "成功"),
    APP_ERROR("500", "系统内部错误"),
    OTHER_ERROR("501", "其他错误");

    private String code;

    private String message;

    CodeEn(String code, String message) {
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
