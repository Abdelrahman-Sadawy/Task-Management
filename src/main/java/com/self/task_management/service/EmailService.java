package com.self.task_management.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final String SERVICE = this.getClass().getSimpleName();
    private final JavaMailSender javaMailSender;

    public void sendPasswordResetEmail(String to, String token) {
        log.info("{}:: sendPasswordResetEmail method: STARTED with emailTo:  {}", SERVICE, to);

        String subject = "Password Reset Request";
        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        String message = "Hello,\n\n" +
                "You requested to reset your password. Click the link below to reset it:\n" +
                resetLink + "\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Thanks,\nYour App Team";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);

        log.info("{}:: sendPasswordResetEmail method: COMPLETED for email:  {}", SERVICE, email);

        javaMailSender.send(email);
    }
}
