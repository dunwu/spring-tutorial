package example.spring.web.websocket.entity;

import java.util.Date;

public class Message {

    // 发送者
    private Long from;

    // 发送者名称
    private String fromName;

    // 接收者
    private Long to;

    // 发送的文本
    private String text;

    // 发送日期
    private Date date;

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
