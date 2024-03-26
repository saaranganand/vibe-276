package com.vibeapp.vibe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ExploreController {
    @Autowired
    private ProfileRepository proRepo;

    @GetMapping("/explore")
    public String getAllMusicians(Model model) {
        System.out.println("Displaying all musicians");
        List<Profile> profiles = proRepo.findAll();
        model.addAttribute("profiles", profiles);
        return "viewAll.html";
    }

    @PostMapping("/search")
    public String searchProfiles(@RequestParam String input, Model model) {
        System.out.println("Searching profiles: " + input);
        List<Profile> filteredProfiles = proRepo.findByNameContainingIgnoreCase(input);
        model.addAttribute("profiles", filteredProfiles);
        return "/viewAll";
    }

    @GetMapping("/users/explore")
    public String getAllMusiciansLogin(Model model) {
        System.out.println("Displaying all musicians");
        List<Profile> profiles = proRepo.findAll();
        model.addAttribute("profiles", profiles);
        return "users/viewAll-loggedin";
    }


    
    
}
