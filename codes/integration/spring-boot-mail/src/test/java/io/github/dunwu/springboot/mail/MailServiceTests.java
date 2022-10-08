package io.github.dunwu.springboot.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @author Zhang Peng
 * @since 2019-01-09
 */
@SpringBootTest(classes = { MailApplication.class })
public class MailServiceTests {

    public static final String[] TO = new String[] { "xxxxx@qq.com", "xxxxx@qq.com" };

    public static final String[] CC = new String[] { "xxxxx@163.com" };

    public static final String SUBJECT = "Test Email";

    @Autowired
    private MailService mailService;

    @Test
    public void sendMimeMessage() {
        MailDto mailDto = new MailDto();
        String text = new StringBuilder().append("<html>\n")
                                         .append("<body>\n")
                                         .append("<h3>This is a mime message email.</h3>\n")
                                         .append("</body>\n")
                                         .append("</html>")
                                         .toString();
        mailDto.setTo(TO);
        mailDto.setCc(CC);
        mailDto.setSubject(SUBJECT);
        mailDto.setText(text);
        mailService.sendMimeMessage(mailDto);
    }

    @Test
    public void sendMimeMessageWithAttachment() {
        MailDto mailDto = new MailDto();
        mailDto.setTo(TO);
        mailDto.setCc(CC);
        mailDto.setSubject(SUBJECT);
        mailDto.setText("This is a mime message email.");
        Resource resource = new ClassPathResource("moon.png");
        Resource resource2 = new ClassPathResource("icon-man.png");
        try {
            String[] filenames = new String[] { resource.getFile().getAbsolutePath(),
                resource2.getFile().getAbsolutePath() };
            mailDto.setFilenames(filenames);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mailService.sendMimeMessage(mailDto);
    }

    @Test
    public void sendSimpleMailMessage() {
        MailDto mailDto = new MailDto();
        mailDto.setTo(TO);
        mailDto.setCc(CC);
        mailDto.setSubject(SUBJECT);
        mailDto.setText("This is a simple message email.");
        mailService.sendSimpleMailMessage(mailDto);
    }

}
