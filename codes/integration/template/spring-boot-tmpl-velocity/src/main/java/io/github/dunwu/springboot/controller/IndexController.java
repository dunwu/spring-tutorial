package io.github.dunwu.springboot.controller;

import com.alibaba.boot.velocity.annotation.VelocityLayout;
import io.github.dunwu.springboot.mail.MailDTO;
import io.github.dunwu.springboot.mail.MailService;
import io.github.dunwu.springboot.util.VelocityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * {@link IndexController}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @version 1.0.0
 * @see IndexController
 * @since 1.0.0 2016-07-18
 */
@Controller
@VelocityLayout("/layout/default.vm") // Default layout page URL in current @Controller
public class IndexController extends BaseController {

    private final MailService mailService;

    @Value("${mail.from}")
    private String mailUrl;

    public IndexController(MailService mailService) {
        this.mailService = mailService;
    }

    @RequestMapping(value = { "/", "/index" })
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = "/tools", method = { RequestMethod.GET, RequestMethod.POST })
    @VelocityLayout("/layout/layout.vm") // Overrides default layout @VelocityLayout("/layout/default.vm")
    public String tools(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        return "tools";
    }

    @RequestMapping("/mail/send")
    @VelocityLayout("/layout/layout.vm")
    public String sendMail(Model model, HttpServletRequest request) {
        model.addAttribute("request", request);
        sendTestMail();
        return "tools";
    }

    public void sendTestMail() {
        VelocityContext context = new VelocityContext();
        context.put("name", "Dunwu");
        context.put("hint", "欢迎使用Velocity邮件模板：");

        // 直接传入一个对象
        context.put("date", new Date());

        // 传入一个Vector
        Hyperlink item1 = new Hyperlink("百度首页", "https://www.baidu.com");
        Hyperlink item2 = new Hyperlink("网易首页", "http://www.163.com/");
        List<Hyperlink> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        context.put("links", list);
        context.put("logo", "https://raw.githubusercontent.com/dunwu/images/master/common/dunwu-logo-200.png");

        MailDTO mailDTO = new MailDTO();
        mailDTO.setTo(new String[] { mailUrl });
        mailDTO.setSubject("TEST MAIL");
        String text = VelocityUtil.getMergeOutput(context, "templates/velocity/default/mail.vm");
        mailDTO.setText(text);
        mailService.sendMimeMessage(mailDTO);
    }

    @Data
    @AllArgsConstructor
    public static class Hyperlink {

        private String link;

        private String name;

    }

}
