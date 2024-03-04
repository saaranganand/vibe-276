package com.vibeapp.vibe.models;

import jakarta.persistence.*;

public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Feedback(String message) {
        this.message = message;
    }

    
}
