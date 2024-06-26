package com.vibeapp.vibe.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer>{
    List<Announcement> findAllByOrderByAidDesc();
    List<Announcement> findAllByOrderByAidAsc();
    Announcement findByAid(int aid);
    List<Announcement> findAllByUploader(String uploader);
}
