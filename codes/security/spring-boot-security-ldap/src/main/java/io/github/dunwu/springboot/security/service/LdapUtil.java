package io.github.dunwu.springboot.security.service;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

public class LdapUtil {

    private final LdapContextSource contextSource;
    private final LdapTemplate ldapTemplate;

    public LdapUtil(LdapContextSource contextSource, LdapTemplate ldapTemplate) {
        this.contextSource = contextSource;
        this.ldapTemplate = ldapTemplate;
    }

    public boolean authentication(String username, String password) throws RuntimeException {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("uid", username));
        return ldapTemplate.authenticate("", filter.toString(), password);
    }

}
