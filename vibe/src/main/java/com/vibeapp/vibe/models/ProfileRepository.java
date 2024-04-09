package com.vibeapp.vibe.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile findByUid(int uid);
    Profile findByName(String name);
    List<Profile> findByNameContainingIgnoreCase(String name);
    List<Profile> findByCityNameContainingIgnoreCase(String city);
    List<Profile> findByInstrumentContainingIgnoreCase(String instrument);
    List<Profile> findByGenresContainingIgnoreCase(String genres);
}
