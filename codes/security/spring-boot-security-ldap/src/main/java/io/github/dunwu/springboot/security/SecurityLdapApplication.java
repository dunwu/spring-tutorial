package io.github.dunwu.springboot.security;

import io.github.dunwu.springboot.security.service.LdapUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-25
 */
@RequiredArgsConstructor
@SpringBootApplication
public class SecurityLdapApplication {

    private final transient Logger log = LoggerFactory.getLogger(this.getClass());
    private final LdapUtil ldapUtil;

    public static void main(String[] args) {
        SpringApplication.run(SecurityLdapApplication.class, args);
    }

    @PostConstruct
    public void tryAuthentication() {
        log.info("{} 认证结果：{}", "zhangsan", ldapUtil.authentication("zhangsan", "123456"));
        log.info("{} 认证结果：{}", "lisi", ldapUtil.authentication("lisi", "123456"));
        log.info("{} 认证结果：{}", "zhaowu", ldapUtil.authentication("zhaowu", "123456"));
    }

}
