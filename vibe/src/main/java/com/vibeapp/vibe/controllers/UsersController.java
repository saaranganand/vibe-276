package com.vibeapp.vibe.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepo;
    
    // registration form validation and account creation
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
            return "users/registerFailed";
        }
        else {
            userRepo.save(new User(newName, newPassword, newEmail));
            response.setStatus(201);
            return "users/registerSuccess";
        }
    }

    @GetMapping("/users/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) { // not logged in
            return "login";
        }
        else { // logged in
            model.addAttribute("user", user);
            return "users/home-loggedin";
        }
    }

    @PostMapping("/users/login")
    public String login(@RequestParam Map<String, String> formData, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // processing login
        String nameOrEmail = formData.get("nameoremail");
        String password = formData.get("password");

        User user = userRepo.findByNameAndPassword(nameOrEmail, password);
        if (user == null) {
            user = userRepo.findByEmailAndPassword(nameOrEmail, password);
        }

        if (user == null) {
            response.setStatus(409);
            return "users/loginFailed";
        } else {
            // login success
            request.getSession().setAttribute("session_user", user);
            model.addAttribute("user", user);
            response.setStatus(201);
            return "users/home-loggedin";
        }
    }

    @PostMapping("/users/profile")
    public String profle(@RequestParam Map<String, String> formData,Model model){
        String username = formData.get("username");
        User user = userRepo.findByName(username);
        model.addAttribute("username", user);
        return "users/add";
    }

    // logout
    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/login";
    }
}
