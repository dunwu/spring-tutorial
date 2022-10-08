package io.github.dunwu.springboot.swagger;

/**
 * @author Zhang Peng
 * @since 2019-01-11
 */
public class ResponseDto<T> {

    private Boolean success;

    private String code;

    private String message;

    private T data;

    public ResponseDto(Boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseDto(Boolean success, CodeEn code, T data) {
        this.success = success;
        this.code = code.name();
        this.message = code.message();
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public enum CodeEn {

        SUCCESS(0, "成功"),
        APP_ERROR(-1, "系统内部错误"),
        OTHER_ERROR(-2, "其他错误");

        private Integer code;

        private String message;

        CodeEn(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer code() {
            return code;
        }

        public String message() {
            return message;
        }
    }

}
