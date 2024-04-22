package com.boats.recipe.webapp.model;

public class Allergen {

    private String name;
    private int id;

    public Allergen(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
