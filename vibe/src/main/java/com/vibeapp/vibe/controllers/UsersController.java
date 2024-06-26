package com.vibeapp.vibe.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibeapp.vibe.models.EmailService;
import com.vibeapp.vibe.models.Token;
import com.vibeapp.vibe.models.TokenRepository;
import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;
import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;



@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProfileRepository profRepo;

    @Autowired
    TokenRepository tokenRepo;

    @Autowired
    EmailService emailService;
    
    // registration form validation and account creation
    @Transactional
    @PostMapping("/users/register")
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
        System.out.println("ADD new user");
        String newEmail = newuser.get("email");
        String token = newuser.get("token");
        String newName = newuser.get("username");
        String newPassword = newuser.get("password");

        User existingUserByEmail = userRepo.findByEmail(newEmail);
        User existingUserByUsername = userRepo.findByName(newName);

        Token userToken = tokenRepo.findByToken(token);

        Date current = Date.from(Instant.now());

        // Initialize default image for user under their profile record
        byte[] image = null;
        try {
            image = readImageToByteArray("static/img/profile.png"); // Adjust the path as needed
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (existingUserByEmail != null || existingUserByUsername != null || userToken == null) {
            response.setStatus(409);
            return "users/registerFailed";
        } 
        else if (!userToken.getEmail().equals(newEmail)){
            response.setStatus(409);
            return "users/registerEmailInvalid";
        }
        else if (userToken.getExpirationDate().before(current)) {
            response.setStatus(410);
            tokenRepo.delete(userToken);
            return "users/tokenExpired";
        }
        else {
            userRepo.save(new User(newName, newPassword, newEmail));
            profRepo.save(new Profile(newName, false, image)); // Initialize w default image
            // profRepo.save(new Profile(newName, false));
            tokenRepo.delete(userToken);
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
            List<Profile> profiles = profRepo.findAll();
            model.addAttribute("profiles", profiles);
            
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

    @PostMapping("/registerEmail")
    public String registerEmail(@RequestParam String email) {
        User user = userRepo.findByEmail(email);
        if (user != null){
            return "redirect:/registerEmailFailed.html"; 
        }
        Random rand = new Random(); 
        int max = 999999;
        int min = 100000;
        int value = rand.nextInt((max - min) + 1) + min; // Generate rand num between [min, max]

        String token = Integer.toString(value);
        tokenRepo.save(new Token(email, token));
        String subject = "Vibe Music Register Token";
        String bodyMessage = "Your token is " + token;
        emailService.sendSimpleMessage(email, subject, bodyMessage);

        return "redirect:/register.html";
    }
    

}
