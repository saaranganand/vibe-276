package com.vibeapp.vibe.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface Userrepository extends JpaRepository<User,Integer>{
    User findByuId(int uId);
    List<User> findByName(String name);
    List<User> findByCityName(String cityName);


}