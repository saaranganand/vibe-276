package com.vibeapp.vibe.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "musicians")
public class Musician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uId;
    
    private String username;
    private String city;
    private String[] genres;
    /* top3 albums */
    private Boolean host;

    public Musician(){

    }
    public Musician(String username, String city, String[] genres, Boolean host) {
        this.username = username;
        this.city = city;
        this.genres = genres;
        this.host = host;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String[] getGenres() {
        return genres;
    }
    public void setGenres(String[] genres) {
        this.genres = genres;
    }
    public Boolean getHost() {
        return host;
    }
    public void setHost(Boolean host) {
        this.host = host;
    }
    

}
