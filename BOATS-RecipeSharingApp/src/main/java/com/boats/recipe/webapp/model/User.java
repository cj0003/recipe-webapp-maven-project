package com.boats.recipe.webapp.model;

import java.sql.Timestamp;

public class User {
    private final int id;
    private final String username;
    private final String password;
    private final String name;
    private final String surname;
    private final String bio;
    private final String email;
    private final Timestamp registrationDate;
    private final byte[] image;
    private final String imageType;

    // Constructor with parameters
    public User(final int id, final String username, final String password, final String name, final String surname, final String bio, final String email, final Timestamp registrationDate, final byte[] image, final String imageType) {
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
    public final String getPassword() {
        return password;
    }

    public final String getUsername() {
        return username;
    }

    public final int getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final String getSurname() {
        return surname;
    }

    public final String getBio() {
        return bio;
    }

    public final String getEmail() {
        return email;
    }

    public final Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public final byte[] getImage() {
        return image;
    }

    public final String getImageType() {
        return imageType;
    }
    // Omitted for brevity
}
