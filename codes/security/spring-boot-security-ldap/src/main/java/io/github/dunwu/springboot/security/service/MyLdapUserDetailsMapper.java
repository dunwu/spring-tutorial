package io.github.dunwu.springboot.security.service;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-26
 */
public class MyLdapUserDetailsMapper extends LdapUserDetailsMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
        Collection<? extends GrantedAuthority> authorities) {
        List<SimpleGrantedAuthority> mockAuthorities = new ArrayList<>();
        // 新建N个角色
        mockAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        mockAuthorities.add(new SimpleGrantedAuthority("USER"));
        return super.mapUserFromContext(ctx, username, mockAuthorities);
    }

}
