package com.vibeapp.vibe.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile findByuId(int uId);
}
