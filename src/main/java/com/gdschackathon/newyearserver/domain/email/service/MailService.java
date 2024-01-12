package com.gdschackathon.newyearserver.domain.email.service;

import com.gdschackathon.newyearserver.domain.email.dto.SendEmailDto;
import com.gdschackathon.newyearserver.domain.penalty.service.PenaltyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Service
public class MailService {

    private final JavaMailSender emailSender;
    private final PenaltyService penaltyService;

    public MailService(JavaMailSender emailSender, PenaltyService penaltyService) {
        this.emailSender = emailSender;
        this.penaltyService = penaltyService;
    }

    /**
     * 챌린지에 성공했을 경우
     */
    public void sendSuccessEmail(List<String> emails) throws Exception {
        emails.forEach(email -> {
            try {
                sendEmail(
                        SendEmailDto.of(
                                "챌린저가 목적을 달성하였습니다...",
                                "재미없지만... 모두 축하해주시져^^",
                                email,
                                "packit0807@gmail.com",
                                "떠벌림 운영자"
                        )
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 챌린지에 실패했을 경우
     */
    public void sendFailEmail(List<String> emails) throws Exception {
        String penaltyUrl = penaltyService.getPenaltyUrl();

        emails.forEach(email -> {
            try {
                sendEmail(
                        SendEmailDto.of(
                                "챌린저가 도전을 실패하였습니다!!!",
                                "챌린저의 벌칙을 집행해주세요!!!" + "<br/><br/><a href=\"" + penaltyUrl + "\">벌칙 확인하기!!</a>",
                                email,
                                "packit0807@gmail.com",
                                "떠벌림 운영자"
                        )
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void sendEmail(SendEmailDto dto)
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
