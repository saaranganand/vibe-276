package com.vibeapp.vibe.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ExtendedViewRepository extends JpaRepository<ExtendedView, Integer> {
    ExtendedView findByUid(int uid);
    ExtendedView findByName(String name);
}