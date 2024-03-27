package com.vibeapp.vibe;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import com.vibeapp.vibe.controllers.ExploreController;
import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;

@WebMvcTest(ExploreController.class)
public class ExploreControllerTest {

    @MockBean
    private ProfileRepository profileRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExploreController exploreController;

    @MockBean
    private Model model;

    @BeforeEach
    void setUp() {
        Profile mockProfile = new Profile("John", "Burnaby", "Guitar", 30, "Beginner", "Artist 1", "Artist 2", "Artist 3", "Genre", true, new byte[0]);
        List<Profile> dummyProfileList = new ArrayList<>();
        dummyProfileList.add(mockProfile);
        
        when(profileRepo.findAll()).thenReturn(dummyProfileList);
    }

    @Test
    public void testGetAllMusicians() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/explore"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("viewAll.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("profiles", hasItem(
                        allOf(
                                hasProperty("name", Matchers.is("John")),
                                hasProperty("cityName", Matchers.is("Burnaby")),
                                hasProperty("age", Matchers.is(30)),
                                hasProperty("instrument", Matchers.is("Guitar")),
                                hasProperty("top1Artist", Matchers.is("Artist 1")),
                                hasProperty("top2Artist", Matchers.is("Artist 2")),
                                hasProperty("top3Artist", Matchers.is("Artist 3")),
                                hasProperty("host", Matchers.is(true))
                        )
                )));
    }

    @Test
    public void testSearchProfiles() throws Exception {
        Profile mockProfile = new Profile("John", "Burnaby", "Guitar", 30, "Beginner", "Artist 1", "Artist 2", "Artist 3", "Genre", true, new byte[0]);
        List<Profile> dummyProfileList = new ArrayList<>();
        dummyProfileList.add(mockProfile);
        String searchTerm = "John";

        when(profileRepo.findByNameContainingIgnoreCase(searchTerm)).thenReturn(dummyProfileList);

        mockMvc.perform(MockMvcRequestBuilders.post("/search")
                .param("input", searchTerm))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/viewAll"))
                .andExpect(MockMvcResultMatchers.model().attribute("profiles", hasItem(
                        allOf(
                                hasProperty("name", Matchers.is("John")),
                                hasProperty("cityName", Matchers.is("Burnaby")),
                                hasProperty("age", Matchers.is(30))
                        )
                )));

    }
}