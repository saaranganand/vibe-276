package com.vibeapp.vibe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vibeapp.vibe.models.Announcement;
import com.vibeapp.vibe.models.AnnouncementRepository;

@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementRepository announceRepo;

    @GetMapping("/announcements")
    public String getAllAnnouncements(Model model) {
        // Fetching announcements and adding to model will be done here
        List<Announcement> announcements = announceRepo.findAll();
        model.addAttribute("announcements", announcements);
        return "announcements";
    }
}
