package io.github.dunwu.springboot.mail;

import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件服务
 *
 * @author Zhang Peng
 * @since 2019-01-09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final MailProperties mailProperties;
    private final JavaMailSender javaMailSender;
    private final Mapper mapper;

    public void sendMimeMessage(MailDto mailDto) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);

            if (StringUtils.isEmpty(mailDto.getFrom())) {
                messageHelper.setFrom(mailProperties.getFrom());
            }
            messageHelper.setTo(mailDto.getTo());
            messageHelper.setSubject(mailDto.getSubject());

            mimeMessage = messageHelper.getMimeMessage();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mailDto.getText(), "text/html;charset=UTF-8");

            // 描述数据关系
            MimeMultipart mm = new MimeMultipart();
            mm.setSubType("related");
            mm.addBodyPart(mimeBodyPart);

            // 添加邮件附件
            if (ArrayUtils.isNotEmpty(mailDto.getFilenames())) {
                for (String filename : mailDto.getFilenames()) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    try {
                        attachPart.attachFile(filename);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mm.addBodyPart(attachPart);
                }
            }

            mimeMessage.setContent(mm);
            mimeMessage.saveChanges();
            log.info("send mail [{}] to {} success!", mailDto.getSubject(), StringUtils.join(mailDto.getCc(), ","));
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Send mail failed", e);
        }

        javaMailSender.send(mimeMessage);
    }

    public void sendSimpleMailMessage(MailDto mailDto) {
        if (StringUtils.isEmpty(mailDto.getFrom())) {
            mailDto.setFrom(mailProperties.getFrom());
        }
        SimpleMailMessage simpleMailMessage = mapper.map(mailDto, SimpleMailMessage.class);
        javaMailSender.send(simpleMailMessage);
    }

}
