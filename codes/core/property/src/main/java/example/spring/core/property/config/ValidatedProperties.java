package example.spring.core.property.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * 自定义属性校验器 {@link ValidatedPropertiesValidator}，在绑定属性时进行参数校验
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see ValidatedPropertiesValidator
 * @since 2019-11-20
 */
@Component
@Validated
@ConfigurationProperties(prefix = "validator")
@PropertySource("classpath:prop/validator.properties")
public class ValidatedProperties {

    @NotNull
    private String host;

    private Integer port;

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}
