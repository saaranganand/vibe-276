package com.vibeapp.vibe.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findByName(String name);
    User findByNameAndPassword(String name, String password);
    User findByEmailAndPassword(String email, String password);
    List<User> findAll();
}