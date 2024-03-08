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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepo;

    // @GetMapping("/")
    // public RedirectView process() {
    // return new RedirectView("login");
    // }

    // @PostMapping("/users/register")
    // public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
    //     System.out.println("ADD new user");
    //     String newEmail = newuser.get("email");
    //     String newName = newuser.get("username");
    //     String newPassword = newuser.get("password");
    //     if(!(newEmail.isEmpty() || newName.isEmpty() || newPassword.isEmpty() || (userRepo.findByEmail(newEmail) != null) || (userRepo.findByName(newName) != null)))
    //     {
    //         userRepo.save(new User(newName, newPassword, newEmail));
    //         response.setStatus(201);
    //         return "/users/registerSuccess";
    //     }
    //     else {
    //         response.setStatus(201);
    //         return "/users/registerFailed";
    //     }
    // }

    @PostMapping("/users/register")
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
        System.out.println("ADD new user");
        String newEmail = newuser.get("email");
        String newName = newuser.get("username");
        String newPassword = newuser.get("password");

        User existingUserByEmail = userRepo.findByEmail(newEmail);
        User existingUserByUsername = userRepo.findByName(newName);
    
        if (existingUserByEmail != null || existingUserByUsername != null) {
            response.setStatus(409);
            return "/users/registerFailed";
        }
        else {
            userRepo.save(new User(newName, newPassword, newEmail));
            response.setStatus(201);
            return "/users/registerSuccess";
        }
    }

    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("session_user");
        if (user != null) {
            return "login";
        }
        else {
            model.addAttribute("user", user);
            return "users/protected";
        }
    }
    
}