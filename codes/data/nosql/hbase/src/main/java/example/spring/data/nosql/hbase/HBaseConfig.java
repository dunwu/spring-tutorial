package example.spring.data.nosql.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "dunwu.data.hbase.enabled", havingValue = "true")
@EnableConfigurationProperties(HBaseProperties.class)
public class HBaseConfig {

    private final HBaseProperties properties;

    public HBaseConfig(HBaseProperties properties) {
        this.properties = properties;
    }

    // HBase Connection 创建是一个耗资源的过程
    // 一般只创建一个 Connection 实例，其它地方共享该实例
    @Bean
    public HBaseAdminHelper hbaseAdminHelper() {
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", properties.getZookeeper().getQuorum());
        conf.set("hbase.zookeeper.port", properties.getZookeeper().getPort().toString());
        try {
            return HBaseAdminHelper.newInstance(conf);
        } catch (IOException e) {
            log.error("create hbase connection failed!", e);
            return null;
        }
    }

}

