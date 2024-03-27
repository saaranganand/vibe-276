package com.vibeapp.vibe;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

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
}
