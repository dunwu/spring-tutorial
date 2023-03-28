package example.spring.data.nosql.hbase;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "dunwu.data.hbase")
public class HBaseProperties implements Serializable {

    private static final long serialVersionUID = 4811135868213066521L;

    private boolean enabled = false;

    private ZooKeeper zookeeper = new ZooKeeper();

    @Data
    public static class ZooKeeper {

        private String quorum;
        private Integer port = 2181;

    }

}
