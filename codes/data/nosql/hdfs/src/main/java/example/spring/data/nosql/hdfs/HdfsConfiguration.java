package example.spring.data.nosql.hdfs;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-02-27
 */
@Configuration
@ConditionalOnProperty(value = "dunwu.data.hdfs.enabled", havingValue = "true")
@EnableConfigurationProperties(HdfsProperties.class)
public class HdfsConfiguration {

    private final HdfsProperties properties;

    public HdfsConfiguration(HdfsProperties properties) {
        this.properties = properties;
    }

    @Bean
    public HdfsFactory hdfsFactory() {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.addResource(new Path(properties.getCoreSiteXmlPath()));
        configuration.addResource(new Path(properties.getHdfsSiteXmlPath()));

        if (properties.getAuth().isEnabled()) {
            System.setProperty("java.security.krb5.conf", properties.getAuth().getKrb5Conf());
            System.setProperty("java.security.auth.login.config", properties.getAuth().getAuthLoginConfig());
            UserGroupInformation.setConfiguration(configuration);
            try {
                UserGroupInformation.loginUserFromKeytab(properties.getAuth().getPrincipal(),
                    properties.getAuth().getAuthKeyTabPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new HdfsFactory(configuration);
    }

    @Bean
    public HdfsPool hdfsPool(HdfsFactory hdfsFactory) {
        GenericObjectPoolConfig<FileSystem> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxWaitMillis(3000);
        return new HdfsPool(hdfsFactory, poolConfig);
    }

    @Bean
    public HdfsUtil hdfsUtil(HdfsPool hdfsPool) {
        return new HdfsUtil(hdfsPool);
    }

}
