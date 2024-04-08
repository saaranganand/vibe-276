package com.vibeapp.vibe.controllers;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;
import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;


@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProfileRepository profRepo;
    
    // registration form validation and account creation
    @Transactional
    @PostMapping("/users/register")
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
        System.out.println("ADD new user");
        String newEmail = newuser.get("email");
        String newName = newuser.get("username");
        String newPassword = newuser.get("password");

        User existingUserByEmail = userRepo.findByEmail(newEmail);
        User existingUserByUsername = userRepo.findByName(newName);

        // Initialize default image for user under their profile record
        byte[] image = null;
        try {
            image = readImageToByteArray("static/img/profile.png"); // Adjust the path as needed
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (existingUserByEmail != null || existingUserByUsername != null) {
            response.setStatus(409);
            return "users/registerFailed";
        }
        else {
            userRepo.save(new User(newName, newPassword, newEmail));
            profRepo.save(new Profile(newName, false, image)); // Initialize w default image
            // profRepo.save(new Profile(newName, false));
            response.setStatus(201);
            return "users/registerSuccess";
        }
    }


    public static byte[] readImageToByteArray(String imagePath) throws IOException {
        ClassPathResource imgFile = new ClassPathResource(imagePath);
        return StreamUtils.copyToByteArray(imgFile.getInputStream());
    }

    @GetMapping("/users/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) { // not logged in
            return "login";
        }
        else { // logged in
            List<Profile> profiles = profRepo.findAll();
            long userCount = userRepo.count();
            model.addAttribute("profiles", profiles);
            model.addAttribute("user", user);
            model.addAttribute("userCount", userCount);
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
            long userCount = userRepo.count();
            request.getSession().setAttribute("session_user", user);
            model.addAttribute("user", user);
            model.addAttribute("userCount", userCount);
            response.setStatus(201);
            return "users/home-loggedin";
        }
    }

    @Transactional
    @PostMapping("/users/profile")
    public String profle(@RequestParam Map<String, String> formData,Model model){

        String username = formData.get("username");
        
        // String username = formData.get("username");
        Profile user = profRepo.findByName(username);
        model.addAttribute("username", user);
        return "users/add";
    }

    // @GetMapping("/profile")
    // public String gotoprofile(@RequestParam("name") String name,Model model){
    //     Profile user = profRepo.findByName(name);
    //     return "add.html"
    // }

    // logout
    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "users/login";
    }

}
