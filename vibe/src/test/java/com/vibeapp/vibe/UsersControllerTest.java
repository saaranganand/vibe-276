package com.vibeapp.vibe;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.vibeapp.vibe.models.EmailService;
import com.vibeapp.vibe.models.Token;
import com.vibeapp.vibe.models.TokenRepository;
import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    TokenRepository tokenRepo;

    @MockBean
    EmailService emailService;

    @Test
    public void testLoginSuccess() throws Exception {
        // Mock user data
        User mockUser = new User("testuser", "testpassword", "testemail@test.com");
        when(userRepository.findByNameAndPassword(anyString(), anyString())).thenReturn(mockUser);

        // Perform request
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .param("nameoremail", "testuser")
                .param("password", "testpassword"))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.view().name("users/home-loggedin"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        // Mock user data
        when(userRepository.findByNameAndPassword(anyString(), anyString())).thenReturn(null);

        // Perform request
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .param("nameoremail", "testuser")
                .param("password", "wrongpassword"))
                .andExpect(MockMvcResultMatchers.status().is(409))
                .andExpect(MockMvcResultMatchers.view().name("users/loginFailed"));
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.findByName(anyString())).thenReturn(null);
        when(tokenRepo.findByToken(anyString())).thenReturn(new Token("testuser@example.com", "123456"));
    
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .param("email", "testuser@example.com")
                .param("token", "123456")
                .param("username", "testuser")
                .param("password", "Test@123"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
