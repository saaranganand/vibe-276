package com.vibeapp.vibe.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/users/forgotpassword")
        public String resetPassword(HttpServletRequest request, @RequestParam String email) {
            User user = userRepo.findByEmail(email);
            if (user==null){
                return "users/loginFailed"; //replace later
            }

            String token = UUID.randomUUID().toString();
            tokenRepo.save(new PasswordResetToken(email, token));
            
            
            return "users/registerSuccess";
        }
    }
