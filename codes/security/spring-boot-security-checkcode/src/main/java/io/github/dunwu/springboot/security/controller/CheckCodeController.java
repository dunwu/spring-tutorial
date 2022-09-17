package io.github.dunwu.springboot.security.controller;

import io.github.dunwu.springboot.security.util.CheckCodeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 校验码 Controller
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-9
 */
@RestController
@RequestMapping("checkcode")
public class CheckCodeController {

    /**
     * 生成校验码图，每次访问会随机生成新的校验码图
     */
    @GetMapping(value = "image")
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
