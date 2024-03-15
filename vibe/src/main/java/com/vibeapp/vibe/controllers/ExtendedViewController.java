package com.vibeapp.vibe.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vibeapp.vibe.models.ExtendedView;
import com.vibeapp.vibe.models.ExtendedViewRepository;


@Controller
public class ExtendedViewController{
    @Autowired
    private ExtendedViewRepository ExRepo;

    @GetMapping("/extendedView")
    public String getUserData(Model model)
    {
        ExtendedView exview = ExRepo.findByUid(1);
        model.addAttribute("exview", exview);
        return "extendedView/extendedView";
    }
    
    
}