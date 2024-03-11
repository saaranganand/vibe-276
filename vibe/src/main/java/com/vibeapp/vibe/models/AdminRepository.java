package com.vibeapp.vibe.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
    Admin findByEmail(String email);
    Admin findByName(String name);
    Admin findByNameAndPassword(String name, String password);
    Admin findByEmailAndPassword(String email, String password);
}
