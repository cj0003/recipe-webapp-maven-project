package com.boats.recipe.webapp.model;

import java.sql.Timestamp;

public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String bio;
    private String email;
    private Timestamp registrationDate;
    private byte[] image;
    private String imageType;

    // Constructor with parameters
    public User(int id, String username, String password, String name, String surname, String bio, String email, Timestamp registrationDate, byte[] image, String imageType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.bio = bio;
        this.email = email;
        this.registrationDate = registrationDate;
        this.image = image;
        this.imageType = imageType;
    }

    // Getters and setters
    // Omitted for brevity
}
