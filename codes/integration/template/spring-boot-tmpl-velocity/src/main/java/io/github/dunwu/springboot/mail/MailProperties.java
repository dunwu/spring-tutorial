package io.github.dunwu.springboot.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Zhang Peng
 * @since 2019-01-09
 */
@Validated
@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    private String domain;

    private String from;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
