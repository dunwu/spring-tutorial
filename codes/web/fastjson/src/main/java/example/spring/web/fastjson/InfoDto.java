package example.spring.web.fastjson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 信息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-02-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoDto {

    private String appName;
    private String version;
    private Date date;

}
