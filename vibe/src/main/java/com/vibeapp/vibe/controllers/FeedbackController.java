package com.vibeapp.vibe.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.vibeapp.vibe.models.Feedback;
import com.vibeapp.vibe.models.FeedbackRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/feedback/sendFeedback")
    public String getMethodName(Model model) {
        model.addAttribute("feedback", model);
        
        return "feedback/feedback";
    }
    

    @PostMapping("/feedback/submit-feedback")
    public String sendFeedback(@RequestParam Map<String, String> submitFeedback) {
        String message = submitFeedback.get("user-feedback");
        feedbackRepository.save(new Feedback(message));

        return "feedback/test";
    }
    

   
    


}
