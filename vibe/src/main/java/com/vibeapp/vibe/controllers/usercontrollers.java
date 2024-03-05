package com.vibeapp.vibe.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.Userrepository;

@Controller
public class usercontrollers{
    @Autowired
    private Userrepository Userrepo;

    @PostMapping("/submit-user-info")
    public String addUser(@RequestParam Map<String,String> newUser){
        String newName = newUser.get("name");
        String newCityName = newUser.get("cityName");
        String newInstrument = newUser.get("instrument");
        int newAge = Integer.parseInt(newUser.get("age"));
        String newTop1artist = newUser.get("top1Artist");
        String newTop2artist = newUser.get("top2Artist");
        String newTop3artist = newUser.get("top3Artist");
        String newGenres = newUser.get("genres");
        Boolean host = Boolean.parseBoolean(newUser.get("host"));
        Userrepo.save(new User(newName, newCityName, newInstrument, newAge, newTop1artist, newTop2artist, newTop3artist, newGenres, host));


        return "redirect:/home.html";

    }
    
}