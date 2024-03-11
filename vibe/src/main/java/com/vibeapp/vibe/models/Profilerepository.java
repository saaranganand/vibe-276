package com.vibeapp.vibe.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface Profilerepository extends JpaRepository<Profile,Integer>{
    Profile findByuId(int uId);
    List<Profile> findByName(String name);
    List<Profile> findByCityName(String cityName);


}
