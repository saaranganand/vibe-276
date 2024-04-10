package com.vibeapp.vibe;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.vibeapp.vibe.controllers.ProfileController;
import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileRepository profileRepository;

    @BeforeEach
    void setUp() {
        Profile mockProfile = new Profile("John Doe", "CityName", "Guitar", 30, "Beginner", "Artist 1", "Artist 2", "Artist 3", "Genre", true, new byte[0]);
        when(profileRepository.findByName(any(String.class))).thenReturn(mockProfile);
    }

    @Test
    public void testAddUserSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image content".getBytes());

        mockMvc.perform(multipart("/submit-user-info")
                .file(file)
                .param("username", "John Doe")
                .param("cityName", "CityName")
                .param("instrument", "Guitar")
                .param("age", "30")
                .param("skilllevel", "Beginner")
                .param("top1Artist", "Artist 1")
                .param("top2Artist", "Artist 2")
                .param("top3Artist", "Artist 3")
                .param("genres", "Genre")
                .param("host", "true"))
                .andExpect(status().isFound())
                .andExpect(view().name("users/home-loggedin"));
    }
}
