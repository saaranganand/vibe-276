package com.vibeapp.vibe.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class ExploreController {
    @Autowired
    private ProfileRepository proRepo;

    @Transactional
    @GetMapping("/explore")
    public String getAllMusicians(Model model) {
        System.out.println("Displaying all musicians");
        List<Profile> profiles = proRepo.findAll();
        model.addAttribute("profiles", profiles);
        return "viewAll.html";
    }

    @Transactional
    @PostMapping("/search")
    public String searchProfiles(@RequestParam String input, Model model) {
        System.out.println("Searching profiles: " + input);

        Set<Profile> filteredProfiles = new HashSet<>();

        if (input != null && !input.trim().isEmpty()){
            filteredProfiles.addAll(proRepo.findByNameContainingIgnoreCase(input));
            filteredProfiles.addAll(proRepo.findByInstrumentContainingIgnoreCase(input));
            filteredProfiles.addAll(proRepo.findByGenresContainingIgnoreCase(input));
        } else {
            filteredProfiles.addAll(proRepo.findAll());
        }
    
        model.addAttribute("profiles", filteredProfiles);
        return "viewAll.html";
    }

    @Transactional
    @PostMapping("/users/search")
    public String searchProf(@RequestParam String input, Model model) {
        System.out.println("Searching profiles: " + input);

        Set<Profile> filteredProfiles = new HashSet<>();

        if (input != null && !input.trim().isEmpty()){
            filteredProfiles.addAll(proRepo.findByNameContainingIgnoreCase(input));
            filteredProfiles.addAll(proRepo.findByInstrumentContainingIgnoreCase(input));
            filteredProfiles.addAll(proRepo.findByGenresContainingIgnoreCase(input));
        } else {
            filteredProfiles.addAll(proRepo.findAll());
        }
    
        model.addAttribute("profiles", filteredProfiles);
        return "users/viewAll-loggedin";
    }

    @Transactional
    @PostMapping("/users/explore")
    public String profle(@RequestParam Map<String, String> formData,Model model){
        String username = formData.get("username");
        Profile user = proRepo.findByName(username);
        model.addAttribute("username", user);
        System.out.println("Displaying all musicians");
        List<Profile> profiles = proRepo.findAll();
        model.addAttribute("profiles", profiles);
        return "users/viewAll-loggedin";
    }


    
    
}
