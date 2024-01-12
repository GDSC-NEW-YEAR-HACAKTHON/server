package com.gdschackathon.newyearserver.domain.email.service;

import com.gdschackathon.newyearserver.domain.email.dto.SendEmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Slf4j
@Service
public class MailService {

    private final JavaMailSender emailSender;

    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(SendEmailDto dto)
            throws Exception {

        log.info("보내는 대상 : {}", dto.to());

        MimeMessage message = setMessage(
                dto.title(),
                dto.content(),
                dto.to(),
                dto.from(),
                dto.senderName()
        );

        try {
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    private MimeMessage setMessage(
            String title,
            String content,
            String to,
            String from,
            String senderName
    ) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject(title);
        message.setText(content, "utf-8", "html");
        message.setFrom(new InternetAddress(from, senderName));

        return message;
    }
}
