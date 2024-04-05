package com.vibeapp.vibe.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibeapp.vibe.models.ExtendedView;
import com.vibeapp.vibe.models.ExtendedViewRepository;
import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;
;

@Controller
public class ExtendedViewController{
    @Autowired
    private ExtendedViewRepository ExRepo;

    @Autowired
    private ProfileRepository proRepo;

    // @GetMapping("/extendedView")
    // public String getUserData(@RequestParam("name") String name, Model model)
    // {
    //     ExtendedView exview = ExRepo.findByName(name);
    //     model.addAttribute("exview", exview);
    //     return "extendedView/extendedView";
    // }
    @Transactional
    @PostMapping("/extendedView")
    public String getUserData(@RequestParam Map<String, String> formData, Model model)
    {
        String username = formData.get("username");
        Profile user = proRepo.findByName(username);
        model.addAttribute("username", user);
        String extendusername = formData.get("explorename");
        ExtendedView exview = ExRepo.findByName(extendusername);
        model.addAttribute("exview", exview);
        return "extendedView/extendedView";
    }

    
    
}