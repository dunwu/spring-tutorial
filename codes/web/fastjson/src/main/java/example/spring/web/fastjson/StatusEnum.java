package example.spring.web.fastjson;

import io.github.dunwu.tool.core.constant.Status;

/**
 * @author Zhang Peng
 * @since 2018-12-29
 */
public enum StatusEnum implements Status {

    SUCCESS(200, "成功"),
    APP_ERROR(500, "系统内部错误"),
    OTHER_ERROR(501, "其他错误");

    private final int code;

    private final String msg;

    StatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
