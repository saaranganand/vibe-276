package com.vibeapp.vibe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.vibeapp.vibe.models.Musician;
import com.vibeapp.vibe.models.MusiciansRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MusiciansController {
    @Autowired
    private MusiciansRepository muRepo;

    @GetMapping("/explore")
    public String getAllMusicians(Model model) {
        System.out.println("Displaying all musicians");
        List<Musician> musicians = muRepo.findAll();
        model.addAttribute("musicians", musicians);
        return "viewAll.html";
    }

    @GetMapping("/users/explore")
    public String getAllMusiciansLogin(Model model) {
        System.out.println("Displaying all musicians");
        List<Musician> musicians = muRepo.findAll();
        model.addAttribute("musicians", musicians);
        return "users/viewAll-loggedin.html";
    }

    @PostMapping("/search")
    public String searchProfiles(@RequestParam String input, Model model) {
        System.out.println("Searching profiles: " + input);
        // You can perform search logic here and update the model accordingly
        List<Musician> filteredMusicians = muRepo.findByUsernameContainingIgnoreCase(input);
        model.addAttribute("musicians", filteredMusicians);
        return "/viewAll"; // Return only the profiles container fragment
    }
}
