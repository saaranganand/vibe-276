package com.vibeapp.vibe;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.vibeapp.vibe.controllers.AnnouncementController;
import com.vibeapp.vibe.models.Announcement;
import com.vibeapp.vibe.models.AnnouncementRepository;
import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;
import com.vibeapp.vibe.models.User;

@WebMvcTest(AnnouncementController.class)
public class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementRepository announcementRepository;

    @MockBean
    private ProfileRepository profileRepository;

    @Test
    public void testGetAllAnnouncements() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", new User());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/announcements")
            .session(session)
            .param("order", "desc"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/announcements"))
            .andReturn();
    }

    @Test
    public void testEditAnnouncement() throws Exception {
        User mockUser = new User("testuser", "testpassword", "testemail@test.com");
        Announcement mockAnnouncement = new Announcement("Test Title", "Test Content", "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg", "Test Uploader", "Test Date");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", mockUser);

        when(announcementRepository.findByAid(any(Integer.class))).thenReturn(mockAnnouncement);

        mockMvc.perform(get("/users/announcements/edit/1").session(session))
            .andExpect(status().isOk())
            .andExpect(view().name("users/edit-announcement"))
            .andExpect(model().attribute("editanno", mockAnnouncement))
            .andExpect(model().attribute("sessionUser", mockUser));
    }

    @Test
    public void testAddAnnouncement() throws Exception {
        User mockUser = new User("testuser", "testpassword", "testemail@test.com");
        Profile mockProfile = new Profile();
        mockProfile.setImage(new byte[0]); // Set image to an empty byte array

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", mockUser);

        when(profileRepository.findByName(any(String.class))).thenReturn(mockProfile);

        mockMvc.perform(post("/users/addannounce")
            .session(session)
            .param("title", "Test Title")
            .param("content", "Test Content")
            .param("image", "Test Image"))
            .andExpect(status().isFound())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/users/announcements"));
    }

    @Test
    public void testDeleteAnnouncement() throws Exception {
        User mockUser = new User("testuser", "testpassword", "testemail@test.com");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", mockUser);

        int aid = 1;

        mockMvc.perform(MockMvcRequestBuilders.post("/users/announcements/delete/" + aid)
            .session(session))
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/users/announcements"));
    }
}