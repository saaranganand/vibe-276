package com.vibeapp.vibe.controllers;

import java.util.List;
import java.util.Map;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibeapp.vibe.models.Announcement;
import com.vibeapp.vibe.models.AnnouncementRepository;
import com.vibeapp.vibe.models.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;

@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementRepository announceRepo;
    @Autowired
    private ProfileRepository proRepo;


    @GetMapping("/users/announcements")
    public String getAllAnnouncements(Model model) {
        System.out.println("Getting all announcements");
        List<Announcement> announcements = announceRepo.findAllByOrderByAidDesc();
        model.addAttribute("anno", announcements);
        return "users/announcements";
    }

    @Transactional
    @PostMapping("/users/announcements")
    public String getAllAnnouncements(@RequestParam Map<String, String> formData,Model model){
        String username = formData.get("username");
        Profile user = proRepo.findByName(username);
        model.addAttribute("username", user);

        System.out.println("Getting all announcements");
        List<Announcement> announcements = announceRepo.findAllByOrderByAidAsc();
        model.addAttribute("anno", announcements);
        return "users/announcements";
    }






    @GetMapping("/users/add-anno")
    public String getMethodName() {
        return "users/add-announcement";
    }
    
    // Post mapping to add new anouncement
    @PostMapping("/users/addannounce")
    public String addStudent(@RequestParam Map<String, String> newannouncement, HttpServletResponse response, HttpSession session) {
        System.out.println("Add announcement");
        String newTitle = newannouncement.get("title");
        String newContent = newannouncement.get("content");

        // get the current date
        String newDate = LocalDate.now().toString();

        String newImage = newannouncement.get("image");
        // get the currently logged in user
        User user = (User) session.getAttribute("session_user");
        String uploader = user.getName();
        announceRepo.save(new Announcement(newTitle, newContent, newImage, uploader, newDate));
        response.setStatus(201);
        return "redirect:/users/announcements";
    }
}
