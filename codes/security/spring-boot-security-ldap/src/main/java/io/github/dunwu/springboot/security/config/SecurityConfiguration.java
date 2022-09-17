package io.github.dunwu.springboot.security.config;

import io.github.dunwu.springboot.security.handler.CustomAuthenticationFailureHandler;
import io.github.dunwu.springboot.security.handler.CustomAuthenticationSuccessHandler;
import io.github.dunwu.springboot.security.service.LdapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;
import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@AutoConfigureAfter(LdapAutoConfiguration.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final LdapProperties ldapProperties;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 模拟数据库查询，判断尝试登陆用户是否满足认证条件
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception 异常
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
            .userDnPatterns("uid={0},ou=users")
            .groupSearchBase("ou=users")
            .contextSource()
            .url("ldap://172.22.6.12:10389/dc=dunwu,dc=github,dc=io")
            .and()
            .passwordCompare()
            .passwordEncoder(new LdapShaPasswordEncoder())
            .passwordAttribute("userPassword");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 设置不需要认证的路径
            .antMatchers("/unauthorized", "/login", "/auth/*.html", "/css/*.css")
            .permitAll()
            // 其余所有请求都需要认证
            .anyRequest()
            .fullyAuthenticated();

        http.formLogin() // 表单登录
            // http.httpBasic() // HTTP Basic
            .loginPage("/unauthorized") // 登录页面
            .loginProcessingUrl("/login") // 处理登录请求表单的 URL
            .successHandler(authenticationSuccessHandler) // 处理登录成功事件
            .failureHandler(authenticationFailureHandler); // 处理登录失败事件

        http.csrf().disable();
    }

    @Bean
    public LdapUtil ldapUtil(LdapContextSource ldapContextSource, LdapTemplate ldapTemplate) {
        return new LdapUtil(ldapContextSource, ldapTemplate);
    }

}
