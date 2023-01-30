package example.spring.core.property.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

/**
 * 从 <code>prop/random.properties</code> 文件中加载随机属性
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see org.springframework.boot.env.RandomValuePropertySource
 * @since 2019-11-20
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "dunwu.random")
@PropertySource("classpath:/prop/random.properties")
public class DunwuRandomProperties {

    private String secret;

    private int number;

    private BigInteger bigNumber;

    private String uuid;

    private int lessThanTenNum;

    private Integer inRangeNum;

}
