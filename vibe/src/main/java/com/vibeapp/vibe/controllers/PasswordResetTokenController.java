package com.vibeapp.vibe.controllers;

import java.time.Instant;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibeapp.vibe.models.EmailService;
import com.vibeapp.vibe.models.Token;
import com.vibeapp.vibe.models.TokenRepository;
import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class PasswordResetTokenController {
    @Autowired 
    UserRepository userRepo;

    @Autowired
    TokenRepository tokenRepo;

    @Autowired
    EmailService emailService;

    @PostMapping("/users/forgotpassword")
        public String resetPassword(HttpServletRequest request, @RequestParam String email) {
            User user = userRepo.findByEmail(email);
            if (user==null){
                return "users/findEmailFailed";
            }

            Random rand = new Random(); 
            int max = 999999;
            int min = 100000;
            int value = rand.nextInt((max - min) + 1) + min; // Generate rand num between [min, max]

            String token = Integer.toString(value);
            tokenRepo.save(new Token(email, token));
            String subject = "Vibe Music Reset Password Token";
            String bodyMessage = "Your token is " + token;
            emailService.sendSimpleMessage(email, subject, bodyMessage);
            
            return "users/editPassword";
        }
        
    @PostMapping("/users/tokenrequest")
        public String changePassword(@RequestParam String token, @RequestParam String password) {
            Token userToken = tokenRepo.findByToken(token);
            if (userToken==null){
                return "users/editPassword";
            } 
            Date current = Date.from(Instant.now());
            if (userToken.getExpirationDate().before(current)) {
                tokenRepo.delete(userToken);
                return "users/tokenExpired";
            }
            
            User user = userRepo.findByEmail(userToken.getEmail());
            user.setPassword(password);
            userRepo.save(user);
            tokenRepo.delete(userToken);

            return "users/login";
        }


    }


