package com.vibeapp.vibe;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SpringTaskConfig {} // Responsible for periodic token deletion in database
