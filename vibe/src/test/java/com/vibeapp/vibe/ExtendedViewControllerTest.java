package com.vibeapp.vibe;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.vibeapp.vibe.controllers.ExtendedViewController;
import com.vibeapp.vibe.models.ExtendedView;
import com.vibeapp.vibe.models.ExtendedViewRepository;
import com.vibeapp.vibe.models.Profile;
import com.vibeapp.vibe.models.ProfileRepository;
import com.vibeapp.vibe.models.User;
import com.vibeapp.vibe.models.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ExtendedViewControllerTest {

    @Mock
    private ExtendedViewRepository extendedViewRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExtendedViewController extendedViewController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = standaloneSetup(extendedViewController).build();
    }

    @Test
    void testGetUserData() throws Exception {
        // Arrange
        String username = "testUser";
        Profile profile = mock(Profile.class);
        ExtendedView extendedView = mock(ExtendedView.class);
        User user = mock(User.class);

        when(profileRepository.findByName(username)).thenReturn(profile);
        when(extendedViewRepository.findByName(username)).thenReturn(extendedView);
        when(userRepository.findByName(username)).thenReturn(user);

        // Act & Assert
        mockMvc.perform(post("/extendedView")
                .param("username", username)
                .param("explorename", username))
                .andExpect(status().isOk())
                .andExpect(view().name("extendedView/extendedView"))
                .andExpect(model().attributeExists("username", "exview", "user1"));
    }

    @Test
    public void testGetUserProfileImage() throws Exception {
        // Arrange
        int userId = 1;
        byte[] mockImage = new byte[] {1, 2, 3, 4};
        ExtendedView extendedView = new ExtendedView();
        extendedView.setUserimage(mockImage);

        when(extendedViewRepository.findByUid(userId)).thenReturn(extendedView);

        // Act
        MockHttpServletResponse response = mockMvc.perform(get("/user/profileimage/" + userId))
                .andReturn().getResponse();

        // Assert
        byte[] responseBody = response.getContentAsByteArray();
        verify(extendedViewRepository).findByUid(userId);
        assertArrayEquals(mockImage, responseBody);
        assertEquals(MediaType.IMAGE_JPEG_VALUE, response.getContentType());
    }
}