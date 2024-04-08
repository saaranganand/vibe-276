package com.vibeapp.vibe.controllers;

import java.util.List;
import java.util.Map;

import javax.swing.Spring;

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


    @Transactional
    @PostMapping("/submit-user-info")
    public String addUser(@RequestParam Map<String,String> newUser,
                    @RequestParam("image") MultipartFile file){
        String newName = newUser.get("username");
        Profile user = Profilerepo.findByName(newName);
        String newCityName = newUser.get("cityName");
        String newInstrument = newUser.get("instrument");
        int newAge = Integer.parseInt(newUser.get("age"));
        String newskilllevel = newUser.get("skilllevel");
        String newTop1artist = newUser.get("top1Artist");
        String newTop2artist = newUser.get("top2Artist");
        String newTop3artist = newUser.get("top3Artist");
        String newGenres = newUser.get("genres");
        String spotify = newUser.get("spotify");
        String facebook = newUser.get("facebook");
        String instagram = newUser.get("instagram");
        String lastfm = newUser.get("lastfm");
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
        user.setName(newName);
        user.setCityName(newCityName);
        user.setInstrument(newInstrument);
        user.setAge(newAge);
        user.setSkilllevel(newskilllevel);
        user.setTop1Artist(newTop1artist);
        user.setTop2Artist(newTop2artist);
        user.setTop3Artist(newTop3artist);
        user.setGenres(newGenres);
        user.setHost(host);
        if(user.getSpotify() ==null){
            user.setSpotify(spotify);
        }
        user.setFacebook(facebook);
        user.setInstagram(instagram);
        user.setLastfm(lastfm);
        if(imageBytes != null){
            user.setImage(imageBytes);
        }
        return "redirect:/users/login";
    }

    // @GetMapping("/userimage")
    // public String getimage(Model model){
    //     List<Profile> profiles = Profilerepo.findAll();
    //     model.addAttribute("check", profiles);
    //     return "users/imagetest";

    // }
    @GetMapping("/user/image/{userId}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable int userId) {
        Profile profile = Profilerepo.findById(userId).orElse(null);
        if (profile != null && profile.getImage() != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust based on your image type
                    .body(profile.getImage());
        } else {
            // Optionally, return a default image if the user has no image
            return ResponseEntity.notFound().build();
        }
    }

}
