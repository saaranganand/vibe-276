package com.vibeapp.vibe.models;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {
    @Autowired
    private JavaMailSender emailSender;

    public void setEmailSender(JavaMailSender emailSender){
        this.emailSender=emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("vibemusicwebsite@gmail.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }

    // private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user) {
    //     String url = contextPath + "/user/changePassword?token=" + token;
    //     String message = messages.getMessage("message.resetPassword", null, locale);
    //     return constructEmail("Reset Password", message + " \r\n" + url, user);
    // }

    // private SimpleMailMessage constructEmail(String subject, String body, User user) {
    //     SimpleMailMessage email = new SimpleMailMessage();
    //     email.setSubject(subject);
    //     email.setText(body);
    //     email.setTo(user.getEmail());
    //     email.setFrom(env.getProperty("support.email"));
    //     return email;
    // }
    
}

