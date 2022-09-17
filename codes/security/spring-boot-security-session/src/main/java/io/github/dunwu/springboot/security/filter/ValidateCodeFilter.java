package io.github.dunwu.springboot.security.filter;

import io.github.dunwu.springboot.security.config.DunwuSecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-8
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    private final DunwuSecurityProperties dunwuSecurityProperties;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    public ValidateCodeFilter(DunwuSecurityProperties dunwuSecurityProperties,
        AuthenticationFailureHandler authenticationFailureHandler) {
        this.dunwuSecurityProperties = dunwuSecurityProperties;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        if (StringUtils.equalsIgnoreCase(dunwuSecurityProperties.getLoginProcessingUrl(), request.getRequestURI())
            && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {

            try {
                check(request);
            } catch (DunwuSecurityException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void check(HttpServletRequest httpServletRequest) throws DunwuSecurityException {

        HttpSession session = httpServletRequest.getSession();
        String code = (String) session.getAttribute("code");
        LocalDateTime expireTime = (LocalDateTime) session.getAttribute("expireTime");
        String checkCode = httpServletRequest.getParameter("checkCode");

        if (StringUtils.isBlank(checkCode)) {

            throw new DunwuSecurityException("验证码不能为空！");
        }

        if (StringUtils.isBlank(code)) {
            throw new DunwuSecurityException("验证码不存在！");
        }

        if (expireTime == null || expireTime.isBefore(LocalDateTime.now())) {
            throw new DunwuSecurityException("验证码已过期！");
        }

        if (!code.equalsIgnoreCase(checkCode)) {
            throw new DunwuSecurityException("验证码不正确！");
        }
    }

}
