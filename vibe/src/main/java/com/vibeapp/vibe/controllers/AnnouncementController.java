package com.vibeapp.vibe.controllers;

import java.util.List;
import java.util.Map;

import java.time.LocalDate;

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

    @Transactional
    @GetMapping("/users/announcements")
    public String getAllAnnouncements(@RequestParam(defaultValue = "desc") String order, Model model, HttpSession session) {
        System.out.println("Getting all announcements");
        List<Announcement> announcements;
        if ("asc".equals(order)) {
            announcements = announceRepo.findAllByOrderByAidAsc();
        } else {
            announcements = announceRepo.findAllByOrderByAidDesc();
        }
        model.addAttribute("anno", announcements);

        User sessionUser = (User) session.getAttribute("session_user");
        if (sessionUser == null) {
            return "redirect:login.html";
        }
        model.addAttribute("sessionUser", sessionUser);

        return "users/announcements";
    }

    @Transactional
    @PostMapping("/users/announcements")
    public String getAllAnnouncements(@RequestParam Map<String, String> formData, Model model, HttpSession session) {
        String username = formData.get("username");
        Profile user = proRepo.findByName(username);
        model.addAttribute("username", user);

        System.out.println("Getting all announcements");
        List<Announcement> announcements = announceRepo.findAllByOrderByAidDesc();

        model.addAttribute("anno", announcements);

        User sessionUser = (User) session.getAttribute("session_user");
        if (sessionUser == null) {
            return "redirect:login.html";
        }
        model.addAttribute("sessionUser", sessionUser);
        return "users/announcements";
    }

    @Transactional
    @GetMapping("/users/announcements/edit/{aid}")
    public String editAnnouncement(@PathVariable Integer aid, Model model, HttpSession session) {
        Announcement anno = announceRepo.findByAid(aid);
        System.out.println(aid);
        if (anno == null) {
            // case where no anno found for given aid
            return "redirect:/users/announcements";
        }
        model.addAttribute("editanno", anno);

        User sessionUser = (User) session.getAttribute("session_user");
        if (sessionUser == null) {
            return "redirect:login.html";
        }
        model.addAttribute("sessionUser", sessionUser);
        return "users/edit-announcement";
    }

    @Transactional
    @PostMapping("/users/updateannounce")
    public String updateAnnouncement(@RequestParam Map<String, String> updateAnno, HttpSession session) {
        System.out.println(updateAnno.get("title"));
        int annoId = Integer.parseInt(updateAnno.get("aid"));
        Announcement anno = announceRepo.findByAid(annoId);
        if (anno != null) {
            anno.setTitle(updateAnno.get("title"));
            anno.setContent(updateAnno.get("content"));
            anno.setImage(updateAnno.get("image"));

            User user = (User) session.getAttribute("session_user");
            String uploader = user.getName();
            Profile profileuser = proRepo.findByName(uploader);
            byte[] userimage = profileuser.getImage();
            anno.setUserimage(userimage);

            announceRepo.save(anno);
            return "redirect:/users/announcements";
        } else {
            return "redirect:/users/announceerror";
        }
    }

    @GetMapping("/users/add-anno")
    public String getMethodName() {
        return "users/add-announcement";
    }

    // Post mapping to add new anouncement
    @Transactional
    @PostMapping("/users/addannounce")
    public String addAnnouncement(@RequestParam Map<String, String> newannouncement, HttpServletResponse response,
            HttpSession session) {
        System.out.println("Add announcement");
        String newTitle = newannouncement.get("title");
        String newContent = newannouncement.get("content");

        // get the current date
        String newDate = LocalDate.now().toString();

        String newImage = newannouncement.get("image");
        // get the currently logged in user
        User user = (User) session.getAttribute("session_user");
        String uploader = user.getName();
        Profile profileuser = proRepo.findByName(uploader);
        byte[] userimage = profileuser.getImage();
        announceRepo.save(new Announcement(newTitle, newContent, newImage, uploader, newDate, userimage));
        response.setStatus(201);
        return "redirect:/users/announcements";
    }

    @GetMapping("/user/userimage/{userId}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable int userId) {
        Announcement annouce = announceRepo.findById(userId).orElse(null);
        if (annouce != null && annouce.getUserimage() != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(annouce.getUserimage());
        } else {
            // Optionally, return a default image if the user has no image
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PostMapping("/users/announcements/delete/{aid}")
    public String deleteAnnouncement(@PathVariable("aid") int aid, HttpServletResponse response, HttpSession session, Model model) {
        System.out.println("Delete announcement");
        announceRepo.deleteById(aid);

        User sessionUser = (User) session.getAttribute("session_user");
        if (sessionUser == null) {
            return "redirect:login.html";
        }
        model.addAttribute("sessionUser", sessionUser);

        response.setStatus(201);
        return "redirect:/users/announcements";
    }
}
