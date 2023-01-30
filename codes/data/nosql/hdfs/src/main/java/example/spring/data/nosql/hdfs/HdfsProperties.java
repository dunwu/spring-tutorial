package example.spring.data.nosql.hdfs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "dunwu.data.hdfs")
public class HdfsProperties extends HdfsConfig {

    private static final long serialVersionUID = 4677597466430931391L;
    private boolean enabled = false;

}
