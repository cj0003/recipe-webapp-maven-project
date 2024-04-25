package com.boats.recipe.webapp.model;

import java.util.Objects;

public class Collection {
    private int id;
    private String title;
    private String description;
    private int creator;

    public Collection(String title, String description, int creator) {
        this.title = title;
        this.description = description;
        this.creator = creator;
    }

    public Collection(int id, String title, String description, int creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creator = creator;
    }

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

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return id == that.id && creator == that.creator && Objects.equals(title, that.title) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, creator);
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                '}';
    }
}
