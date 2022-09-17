package io.github.dunwu.springboot.mail;

import com.github.dozermapper.core.Mapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author Zhang Peng
 * @since 2019-01-09
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private MailProperties mailProperties;

    private JavaMailSender javaMailSender;

    private Mapper mapper;

    public MailService(MailProperties mailProperties, JavaMailSender javaMailSender,
        Mapper mapper) {
        this.mailProperties = mailProperties;
        this.javaMailSender = javaMailSender;
        this.mapper = mapper;
    }

    public void sendMimeMessage(MailDTO mailDTO) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);

            if (StringUtils.isEmpty(mailDTO.getFrom())) {
                messageHelper.setFrom(mailProperties.getFrom());
            }
            messageHelper.setTo(mailDTO.getTo());
            messageHelper.setSubject(mailDTO.getSubject());

            mimeMessage = messageHelper.getMimeMessage();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mailDTO.getText(), "text/html;charset=UTF-8");

            // 描述数据关系
            MimeMultipart mm = new MimeMultipart();
            mm.setSubType("related");
            mm.addBodyPart(mimeBodyPart);

            // 添加邮件附件
            if (ArrayUtils.isNotEmpty(mailDTO.getFilenames())) {
                for (String filename : mailDTO.getFilenames()) {
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
            javaMailSender.send(mimeMessage);
            log.info("send mail [{}] to {} success!", mailDTO.getSubject(), StringUtils.join(mailDTO.getTo(), ","));
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Send mail failed", e);
        }
    }

    public void sendSimpleMailMessage(MailDTO mailDTO) {
        if (StringUtils.isEmpty(mailDTO.getFrom())) {
            mailDTO.setFrom(mailProperties.getFrom());
        }
        SimpleMailMessage simpleMailMessage = mapper.map(mailDTO, SimpleMailMessage.class);
        javaMailSender.send(simpleMailMessage);
    }

}
