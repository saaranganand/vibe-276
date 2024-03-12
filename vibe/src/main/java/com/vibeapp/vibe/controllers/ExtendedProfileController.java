package com.vibeapp.vibe.controllers;

import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ExtendedProfileController {

    @Autowired
    private final ProfileRepository profileRepository;

    public ExtendedProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping("/profiles/{uId}")
    public String getProfilePage(@PathVariable int uId, Model model) {
        Profile profile = profileRepository.findByuId(uId);
        model.addAttribute("profile", profile);
        return "user/extendedViewPage";
    }
}