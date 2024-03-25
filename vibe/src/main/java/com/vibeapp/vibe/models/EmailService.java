package com.vibeapp.vibe.models;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Async
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("vibemusicwebsite@gmail.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        mailSender.send(message);
    }
    
}
