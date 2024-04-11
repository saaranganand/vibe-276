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

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vibeapp.vibe.controllers.PasswordResetTokenController;
import com.vibeapp.vibe.models.EmailService;
import com.vibeapp.vibe.models.Token;
import com.vibeapp.vibe.models.TokenRepository;
import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;


@WebMvcTest(PasswordResetTokenController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PasswordResetTokenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenRepository tokenRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private EmailService emailService;

    @BeforeEach
    void init(){
        String email = "test@gmail.com";
        String token = "123456";
        Token passwordResetToken = new Token(email, token);
        when(tokenRepo.findByToken(token)).thenReturn(passwordResetToken);
        User user = new User("John", "password", email);
        when(userRepo.findByEmail(passwordResetToken.getEmail())).thenReturn(user);
        when(userRepo.save(user)).thenReturn(user);
    }

    @Test
    void testChangePasswordSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users/tokenrequest")
                .param("token", "123456")
                .param("password", "newPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
                assertEquals("newPassword", userRepo.findByEmail("test@gmail.com").getPassword());
    }   

    @Test
    void testChangePasswordTokenNoExist() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users/tokenrequest")
                .param("token", "000000")
                .param("password", "newPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/editPassword"));
    }
    
    @Test
    void testChangePasswordTokenExpired() throws Exception{
        Token expiredToken = new Token("test@gmail.com", "123456");
        expiredToken.setExpirationDate(Date.from(Instant.now().minus(Duration.ofDays(1))));
        when(tokenRepo.findByToken("777777")).thenReturn(expiredToken);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/tokenrequest")
                .param("token", "777777")
                .param("password", "newPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/tokenExpired"));
                verify(tokenRepo).delete(expiredToken);
    }   

    @Test
    void resetPasswordSuccess() throws Exception{
        when(userRepo.findByEmail("random@gmail.com")).thenReturn(new User());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/forgotpassword")
                .param("email", "random@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/editPassword"));
    }

    @Test
    void resetPasswordFailed() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/users/forgotpassword")
                .param("email", "no@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/findEmailFailed"));
    }

   
    
}
