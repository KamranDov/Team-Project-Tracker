package com.crocusoft.teamprojecttracker.service;

import com.crocusoft.teamprojecttracker.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    @Value("${spring.mail.username}")
    private String mailSenderUsername;

private final JavaMailSender javaMailSender;

    public void sendMail(EmailDto emailDto) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(mailSenderUsername);
            simpleMailMessage.setTo(emailDto.getEmail());
            simpleMailMessage.setSubject(emailDto.getSubject());
            simpleMailMessage.setText(emailDto.getMessage());

            javaMailSender.send(simpleMailMessage);
    }
}
