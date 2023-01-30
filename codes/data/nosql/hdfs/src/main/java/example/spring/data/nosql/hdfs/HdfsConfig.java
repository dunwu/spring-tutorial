package example.spring.data.nosql.hdfs;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.fs.FileSystem;

import java.io.Serializable;

/**
 * Hdfs 配置选项
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-21
 */
@Data
@ToString
@Accessors(chain = true)
public class HdfsConfig implements Serializable {

    private static final long serialVersionUID = -5434086838792181903L;
    /** 认证配置 */
    protected final Auth auth = new Auth();
    /** 连接池配置 */
    protected final Pool pool = new Pool();
    /** hdfs 开关 */
    protected boolean enabled;
    /** core-site.xml 路径 */
    protected String coreSiteXmlPath;
    /** hdfs-site.xml 路径 */
    protected String hdfsSiteXmlPath;

    public static class Pool extends GenericObjectPoolConfig<FileSystem> {}

    @Data
    @Accessors(chain = true)
    public static class Auth implements Serializable {

        private static final long serialVersionUID = 6143900047496149154L;
        private boolean enabled;
        private String principal;
        private String krb5Conf;
        private String authLoginConfig;
        private String authKeyTabPath;

    }

}
