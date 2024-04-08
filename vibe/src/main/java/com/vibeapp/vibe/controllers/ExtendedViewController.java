package com.vibeapp.vibe.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibeapp.vibe.models.ExtendedView;
import com.vibeapp.vibe.models.ExtendedViewRepository;
import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;
import com.vibeapp.vibe.models.UserRepository;
import com.vibeapp.vibe.models.User;
;

@Controller
public class ExtendedViewController{
    @Autowired
    private ExtendedViewRepository ExRepo;

    @Autowired
    private ProfileRepository proRepo;

    @Autowired
    private UserRepository userRepo;

    // @GetMapping("/extendedView")
    // public String getUserData(@RequestParam("name") String name, Model model)
    // {
    //     ExtendedView exview = ExRepo.findByName(name);
    //     model.addAttribute("exview", exview);
    //     return "extendedView/extendedView";
    // }
    // @Transactional
    // @GetMapping("/message")
    // public String getUserEmail(@RequestParam("name") String name, Model model) {
    //     User user = userRepo.findByName(name);
    //     model.addAttribute("user", user);
    //     return "message";
    // }


    @Transactional
    @PostMapping("/extendedView")
    public String getUserData(@RequestParam Map<String, String> formData, Model model)
    {
        String username = formData.get("username");
        Profile user = proRepo.findByName(username);
        
        // User user1 = userRepo.findByName(username);
        model.addAttribute("username", user);
        

        String extendusername = formData.get("explorename");
        ExtendedView exview = ExRepo.findByName(extendusername);
        User user1 = userRepo.findByName(extendusername);
        model.addAttribute("exview", exview);
        model.addAttribute("user1", user1);
        return "extendedView/extendedView";
    }

    @GetMapping("/user/profileimage/{userId}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable int userId) {
        ExtendedView exuser = ExRepo.findByUid(userId);
        if (exuser != null && exuser.getUserimage() != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust based on your image type
                    .body(exuser.getUserimage());
        } else {
            // Optionally, return a default image if the user has no image
            return ResponseEntity.notFound().build();
        }
    }
    
    
}