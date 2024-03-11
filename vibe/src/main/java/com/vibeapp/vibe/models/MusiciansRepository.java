package com.vibeapp.vibe.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MusiciansRepository extends JpaRepository<Musician, Integer>{
    List<Musician> findByUsernameContainingIgnoreCase(String input);

}