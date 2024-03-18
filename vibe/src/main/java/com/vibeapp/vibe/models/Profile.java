package com.vibeapp.vibe.models;

import jakarta.persistence.*;
// import javax.persistence.Entity;




@Entity
@Table(name="profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String name;
    private String cityName;
    private String instrument; // Assuming one instrument
    private int age;
    private String top1Artist;
    private String top2Artist;
    private String top3Artist;
    private String genres;
    private Boolean host;
    // private String image; 
    public Profile(){
    }

    public Profile(String name, String cityName,String instrument,int age,String top1Artist,
    String top2Artist,String top3Artist,String genres,Boolean host){
        this.name = name;
        this.cityName = cityName;
        this.instrument = instrument;
        this.age = age;
        this.top1Artist = top1Artist;
        this.top2Artist = top2Artist;
        this.top3Artist = top3Artist;
        this.genres = genres;
        this.host = host;
    }

    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getInstrument() {
        return instrument;
    }
    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getTop1Artist() {
        return top1Artist;
    }
    public void setTop1Artist(String top1Artist) {
        this.top1Artist = top1Artist;
    }
    public String getTop2Artist() {
        return top2Artist;
    }
    public void setTop2Artist(String top2Artist) {
        this.top2Artist = top2Artist;
    }
    public String getTop3Artist() {
        return top3Artist;
    }
    public void setTop3Artist(String top3Artist) {
        this.top3Artist = top3Artist;
    }
    public String getGenres() {
        return genres;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }
    public Boolean isHost() {
        return host;
    }
    public void setHost(boolean host) {
        this.host = host;
    }
    
}