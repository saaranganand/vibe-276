package com.vibeapp.vibe.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibeapp.vibe.models.EmailService;
import com.vibeapp.vibe.models.EmailServiceImpl;
import com.vibeapp.vibe.models.PasswordResetToken;
import com.vibeapp.vibe.models.PasswordResetTokenRepository;
import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PasswordResetTokenController {
    @Autowired 
    UserRepository userRepo;

    @Autowired
    PasswordResetTokenRepository tokenRepo;

    @Autowired
    EmailService emailService;


    @PostMapping("/users/forgotpassword")
        public String resetPassword(HttpServletRequest request, @RequestParam String email) {
            User user = userRepo.findByEmail(email);
            if (user==null){
                return "users/loginFailed"; //replace later
            }

            // String token = UUID.randomUUID().toString();
            // tokenRepo.save(new PasswordResetToken(email, token));
            emailService.sendSimpleMessage("vibemusicwebsite@gmail.com", "Test Subject", "Text Body");
            
            return "users/editPassword";
        }
    }
