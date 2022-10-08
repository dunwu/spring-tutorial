package io.github.dunwu.springboot.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * 邮件配置
 *
 * @author Zhang Peng
 * @since 2019-01-09
 */
@Data
@Validated
@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    private String domain;

    private String from;

}
