package com.boats.recipe.webapp.model;

import java.sql.Timestamp;

public class Recipe {

    private int id;
    private String title;
    private String description;
    private String ingredients;
    private String method;
    private int preparationTime;
    private int author;
    private int numberOfLikes;
    private Timestamp uploadDate;
    private boolean allergyTrigger;
    private byte[] image;
    private String imageType;

    // Constructor
    public Recipe(String title, String description, String ingredients, String method, int preparationTime, int author, int numberOfLikes, Timestamp uploadDate, boolean allergyTrigger, byte[] image, String imageType) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.method = method;
        this.preparationTime = preparationTime;
        this.author = author;
        this.numberOfLikes = numberOfLikes;
        this.uploadDate = uploadDate;
        this.allergyTrigger = allergyTrigger;
        this.image = image;
        this.imageType = imageType;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public boolean isAllergyTrigger() {
        return allergyTrigger;
    }

    public void setAllergyTrigger(boolean allergyTrigger) {
        this.allergyTrigger = allergyTrigger;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
