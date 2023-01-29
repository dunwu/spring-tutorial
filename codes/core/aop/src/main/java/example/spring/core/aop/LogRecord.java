package example.spring.core.aop;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author peng.zhang
 * @date 2020/9/1 10:25
 */
@Data
@Accessors(chain = true)
public class LogRecord {

    private Long id;
    private String description;
    private String level;
    private String exception;
    private String method;
    private String params;
    private String requestTime;
    private Date createTime;

    public LogRecord() {}

    public LogRecord(String level) {
        this.level = level;
    }

}
