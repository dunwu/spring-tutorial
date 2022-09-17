package io.github.dunwu.springboot.security.config;

import lombok.Data;

@Data
public class ShiroProperties {

    private long sessionTimeout;

    private int cookieTimeout;

    private String anonUrl;

    private String loginUrl;

    private String successUrl;

    private String logoutUrl;

    private String unauthorizedUrl;

}
