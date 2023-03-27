package example.spring.data.nosql.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
public class HBaseConfig {

    // HBase Connection 创建是一个耗资源的过程
    // 一般只创建一个 Connection 实例，其它地方共享该实例
    @Bean
    public HBaseAdminHelper hbaseAdminHelper() {
        String zkHosts = "localhost";
        String zkPort = "2181";
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zkHosts);
        conf.set("hbase.zookeeper.port", zkPort);
        try {
            return HBaseAdminHelper.newInstance(conf);
        } catch (IOException e) {
            log.error("create hbase connection failed!", e);
            return null;
        }
    }

}
