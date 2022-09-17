package io.github.dunwu.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author Zhang Peng
 * @since 2019-01-09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootMailApplication.class })
public class MailServiceTests {

    public static final String[] TO = new String[] { "xxxxx@qq.com", "xxxxx@qq.com" };

    public static final String[] CC = new String[] { "xxxxx@163.com" };

    public static final String SUBJECT = "Test Email";

    @Autowired
    private MailService mailService;

    @Test
    public void sendMimeMessage() {
        MailDTO mailDTO = new MailDTO();

        String text = new StringBuilder().append("<html>\n")
            .append("<body>\n")
            .append("<h3>This is a mime message email.</h3>\n")
            .append("</body>\n")
            .append("</html>")
            .toString();
        mailDTO.setTo(TO);
        mailDTO.setCc(CC);
        mailDTO.setSubject(SUBJECT);
        mailDTO.setText(text);
        mailService.sendMimeMessage(mailDTO);
    }

    @Test
    public void sendMimeMessageWithAttachment() {
        MailDTO mailDTO = new MailDTO();
        mailDTO.setTo(TO);
        mailDTO.setCc(CC);
        mailDTO.setSubject(SUBJECT);
        mailDTO.setText("This is a mime message email.");
        Resource resource = new ClassPathResource("moon.png");
        Resource resource2 = new ClassPathResource("icon-man.png");
        try {
            String[] filenames = new String[] { resource.getFile().getAbsolutePath(),
                resource2.getFile().getAbsolutePath() };
            mailDTO.setFilenames(filenames);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mailService.sendMimeMessage(mailDTO);
    }

    @Test
    public void sendSimpleMailMessage() {
        MailDTO mailDTO = new MailDTO();
        mailDTO.setTo(TO);
        mailDTO.setCc(CC);
        mailDTO.setSubject(SUBJECT);
        mailDTO.setText("This is a simple message email.");
        mailService.sendSimpleMailMessage(mailDTO);
    }

}
