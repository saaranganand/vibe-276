package com.vibeapp.vibe.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ProfileController{
    @Autowired
    private ProfileRepository Profilerepo;
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);



    @PostMapping("/submit-user-info")
    public String addUser(@RequestParam Map<String,String> newUser,
                    @RequestParam("image") MultipartFile file){
        String newName = newUser.get("name");
        String newCityName = newUser.get("cityName");
        String newInstrument = newUser.get("instrument");
        int newAge = Integer.parseInt(newUser.get("age"));
        String newskilllevel = newUser.get("skilllevel");
        String newTop1artist = newUser.get("top1Artist");
        String newTop2artist = newUser.get("top2Artist");
        String newTop3artist = newUser.get("top3Artist");
        String newGenres = newUser.get("genres");
        Boolean host = Boolean.parseBoolean(newUser.get("host"));
        byte[] imageBytes = null;
        if (!file.isEmpty()) {
            try {
                imageBytes = file.getBytes();
                logger.info("Saving image with size: " + imageBytes.length + " bytes");
            } catch (IOException e) {
                // Handle the error condition
                // e.printStackTrace();
                logger.error("Error while getting bytes from image", e);
            }
        }

        Profilerepo.save(new Profile(newName, newCityName, newInstrument, newAge,newskilllevel,newTop1artist, newTop2artist, newTop3artist, newGenres, host,imageBytes));
        

        return "users/home-loggedin";

    }
    
    

}
