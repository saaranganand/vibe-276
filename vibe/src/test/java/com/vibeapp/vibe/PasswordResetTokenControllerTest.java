package com.vibeapp.vibe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.vibeapp.vibe.controllers.PasswordResetTokenController;
import com.vibeapp.vibe.models.EmailService;
import com.vibeapp.vibe.models.PasswordResetToken;
import com.vibeapp.vibe.models.PasswordResetTokenRepository;
import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;


@WebMvcTest(PasswordResetTokenController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PasswordResetTokenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordResetTokenRepository tokenRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private EmailService emailService;

    @BeforeEach
    void init(){
        String email = "test@gmail.com";
        String token = "1234";
        PasswordResetToken passwordResetToken = new PasswordResetToken(email, token);
        when(tokenRepo.findByToken(token)).thenReturn(passwordResetToken);
        User user = new User("John", "password", email);
        when(userRepo.findByEmail(passwordResetToken.getEmail())).thenReturn(user);
        when(userRepo.save(user)).thenReturn(user);
    }

    @Test
    void testChangePasswordSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users/tokenrequest")
                .param("token", "1234")
                .param("password", "newPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
                assertEquals("newPassword", userRepo.findByEmail("test@gmail.com").getPassword());
    }   

   
    
}
