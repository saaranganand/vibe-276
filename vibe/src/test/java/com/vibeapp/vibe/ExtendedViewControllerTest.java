package com.vibeapp.vibe;

import com.vibeapp.vibe.models.ExtendedView;
import com.vibeapp.vibe.models.ExtendedViewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.vibeapp.vibe.controllers.ExtendedViewController;
import com.vibeapp.vibe.models.ExtendedView;
import com.vibeapp.vibe.models.ExtendedViewRepository;

public class ExtendedViewControllerTest {

    @InjectMocks
    ExtendedViewController controller;

    @Mock
    ExtendedViewRepository repository;

    @Mock
    Model model;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetUserData() throws Exception {
        ExtendedView exview = new ExtendedView();
        when(repository.findByName("testName")).thenReturn(exview);

        mockMvc.perform(get("/extendedView").param("name", "testName"))
                .andExpect(status().isOk())
                .andExpect(view().name("extendedView/extendedView"));
    }
}