package io.github.dunwu.springboot.security.controller;

import io.github.dunwu.springboot.security.util.CheckCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-14
 */
@RestController
@RequestMapping("login")
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @GetMapping("unauthorized")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("{} 需要认证后才能访问", targetUrl);
            redirectStrategy.sendRedirect(request, response, "/auth/login.html");
        }
        return "访问的资源需要身份认证！";
    }

    /**
     * 生成校验码图，每次访问会随机生成新的校验码图
     */
    @GetMapping("checkcode")
    public void authImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 生成随机字串
        CheckCodeUtils.CheckCode checkCode = CheckCodeUtils.create(60);
        // 存入会话session
        HttpSession session = request.getSession(true);
        // 删除以前的
        session.removeAttribute("code");
        session.removeAttribute("expireTime");
        session.setAttribute("code", checkCode.getCode());
        session.setAttribute("expireTime", checkCode.getExpireTime());
        OutputStream out = response.getOutputStream();
        CheckCodeUtils.toOutputStream(checkCode, out);
    }

}
