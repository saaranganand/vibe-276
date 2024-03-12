package com.vibeapp.vibe.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibeapp.vibe.models.Admin;
import com.vibeapp.vibe.models.AdminRepository;
import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminsController {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/admins/login")
    public String getAdminLogin(Model model, HttpServletRequest request, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("session_admin");
        if (admin == null) { // not logged in
            return "admin";
        } else { // logged in
            model.addAttribute("admin", admin);
            return "admins/view";
        }
    }

    @PostMapping("/admins/login")
    public String adminLogin(@RequestParam Map<String, String> formData, Model model, HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        // processing login
        String nameOrEmail = formData.get("nameoremail");
        String password = formData.get("password");

        Admin admin = adminRepo.findByNameAndPassword(nameOrEmail, password);
        if (admin == null) {
            admin = adminRepo.findByEmailAndPassword(nameOrEmail, password);
        }

        if (admin == null) {
            response.setStatus(409);
            return "admins/loginFailed";
        } else {
            // login success
            request.getSession().setAttribute("session_admin", admin);
            model.addAttribute("admin", admin);
            System.out.println("Getting all users for deletion table");
            // get all users from database
            List<User> users = userRepo.findAllByOrderByUidAsc();
            model.addAttribute("user", users);
            response.setStatus(201);
            return "admins/view";
        }
    }

    @PostMapping("/admins/del")
    public String delStudents(@RequestParam("userIds") List<Integer> userIds, HttpServletResponse response) {
        if (userIds == null) {
            response.setStatus(400);
            return "admins/noUsersSelected";
        } else {
            System.out.println("Delete user");
            userRepo.deleteAllById(userIds);
            response.setStatus(201);
            return "admins/deleted";
        }
    }

    @PostMapping("/admins/back")
    public String postMethodName(Model model, HttpServletResponse response) {
        System.out.println("Getting all users for deletion table");
        // get all users from database
        List<User> users = userRepo.findAllByOrderByUidAsc();
        model.addAttribute("user", users);
        response.setStatus(201);
        return "admins/view";
    }

    // logout
    @GetMapping("/admins/logout")
    public String destroyAdminSession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "admins/login";
    }
}
