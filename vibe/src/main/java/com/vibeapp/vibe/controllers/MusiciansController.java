package com.vibeapp.vibe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.vibeapp.vibe.models.Musician;
import com.vibeapp.vibe.models.MusiciansRepository;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MusiciansController {
    @Autowired
    private MusiciansRepository muRepo;

    @GetMapping("/users/explore")
    public String getAllMusicians(Model model) {
        System.out.println("Displaying all musicians");
        List<Musician> musicians = muRepo.findAll();
        model.addAttribute("musicians", musicians);
        return "users/viewAll.html";
    }
    
}
