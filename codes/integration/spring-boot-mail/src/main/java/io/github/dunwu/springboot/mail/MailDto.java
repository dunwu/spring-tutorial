package io.github.dunwu.springboot.mail;

import lombok.Data;

import java.util.Date;

/**
 * 邮件信息
 *
 * @author Zhang Peng
 * @since 2019-01-09
 */
@Data
public class MailDto {

    private String from;

    private String replyTo;

    private String[] to;

    private String[] cc;

    private String[] bcc;

    private Date sentDate;

    private String subject;

    private String text;

    private String[] filenames;

}
