package org.zp.notes.spring.web.controller.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 消息响应实体
 * @author Zhang Peng
 */
public class ResponseDTO<T> {

    /**
     * 返回码
     */
    private Integer code = ResponseResultEnum.SUCCESS.code();

    /**
     * 返回消息
     */
    private final List<String> messages = new ArrayList<String>();

    /**
     * 返回数据
     */
    private T data;

    public ResponseDTO() {
        this.messages.add(ResponseResultEnum.SUCCESS.desc());
    }

    public ResponseDTO(T dto) {
        this.messages.add(ResponseResultEnum.SUCCESS.desc());
        this.data = dto;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void addMsg(String msg) {
        this.messages.add(msg);
    }

    public void addMsgs(String[] msgs) {
        this.addMsgs(Arrays.asList(msgs));
    }

    public void addMsgs(List<String> msgs) {
        this.messages.addAll(msgs);
    }

    public void removeMsg(String msg) {
        this.messages.remove(msg);
    }

    public List<String> getMessages() {
        return messages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 响应结果枚举，根据需要增删
     */
    public enum ResponseResultEnum {
        SUCCESS(0, "成功"),
        FAILED(-1, "失败"),

        PARAM_CHECK_FAILED(1001, "参数校验错误");

        private final Integer code;
        private final String desc;

        ResponseResultEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int code() {
            return code;
        }

        public String desc() {
            return desc;
        }
    }
}
