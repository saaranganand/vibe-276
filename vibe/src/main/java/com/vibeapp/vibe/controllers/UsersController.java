package com.vibeapp.vibe.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepo;

    // @GetMapping("/")
    // public RedirectView process() {
    // return new RedirectView("login");
    // }

    @PostMapping("/users/register")
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
        System.out.println("ADD new user");
        String newEmail = newuser.get("email");
        String newName = newuser.get("username");
        String newPassword = newuser.get("password");
        userRepo.save(new User(newName, newPassword, newEmail));
        response.setStatus(201);
        return "/users/registerSuccess";
    }
}