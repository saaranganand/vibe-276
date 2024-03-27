package com.vibeapp.vibe;

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

import com.vibeapp.vibe.controllers.AnnouncementController;
import com.vibeapp.vibe.models.Announcement;
import com.vibeapp.vibe.models.AnnouncementRepository;
import com.vibeapp.vibe.models.User;

import java.util.Arrays;

@WebMvcTest(AnnouncementController.class)
public class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementRepository announcementRepository;

    @Test
    public void testGetAllAnnouncements() throws Exception {
        when(announcementRepository.findAllByOrderByAidAsc()).thenReturn(Arrays.asList(new Announcement("Test Title", "Test Content", "Test Image", "Test Uploader", "Test Date")));

        mockMvc.perform(get("/users/announcements"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/announcements"));
    }

    @Test
    public void testAddAnnouncement() throws Exception {
    User mockUser = new User("testuser", "testpassword", "testemail@test.com");
    
    // Mock session
    MockHttpSession session = new MockHttpSession();
    session.setAttribute("session_user", mockUser);

    when(announcementRepository.save(any(Announcement.class))).thenReturn(new Announcement("Test Title", "Test Content", "Test Image", "Test Uploader", "Test Date"));

    mockMvc.perform(post("/users/addannounce")
        .param("title", "Test Title")
        .param("content", "Test Content")
        .param("image", "Test Image")
        .session(session))
        .andExpect(status().isFound())
        .andExpect(view().name("redirect:/users/announcements"));
    }
}