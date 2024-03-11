package com.vibeapp.vibe.controllers;

import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping("/profiles/{uId}")
    public String getProfilePage(@PathVariable int uId, Model model) {
        Profile profile = profileRepository.findByUId(uId);
        if (profile != null) {
            model.addAttribute("profile", profile);
            return "user/extendedViewPage";
        } else {
            model.addAttribute("errorMessage", "Profile not found");
            return "error";
        }
    }
}