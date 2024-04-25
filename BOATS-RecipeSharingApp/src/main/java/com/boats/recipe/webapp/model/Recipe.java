package com.boats.recipe.webapp.model;

import com.boats.recipe.webapp.resources.AbstractResource;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;

public class Recipe extends AbstractResource {

    private int id;
    private String title;
    private String description;
    private String ingredients;
    private String instructions;
    private int preparationTime;
    private int author;
    private int numberOfLikes;
    private Timestamp uploadDate;
    private boolean allergyTrigger;
    private byte[] image;
    private String imageType;

    // Constructor
    public Recipe(int id, String title, String description, String ingredients, String instructions, int preparationTime, int author, int numberOfLikes, Timestamp uploadDate, boolean allergyTrigger, byte[] image, String imageType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
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

    public String getInstructions(){return instructions;}

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

    @Override
    protected final void writeJSON(final OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeFieldName("recipe");
        jg.writeStartObject();
        jg.writeNumberField("id", id);
        jg.writeStringField("title", title);
        jg.writeStringField("desc", description);
        jg.writeStringField("ingredients", ingredients);
        jg.writeStringField("instructions", instructions);
        jg.writeNumberField("prep_time", preparationTime);
        jg.writeNumberField("author", author);
        jg.writeNumberField("likes_num", numberOfLikes);
        jg.writeStringField("upload_date", uploadDate.toString());
        jg.writeBooleanField("allergy_trigger", allergyTrigger);
        // If needed, add fields for image and imageType
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }

    public static Recipe fromJSON(final InputStream in) throws IOException {
        int jId = -1;
        String jTitle = null;
        String jDesc = null;
        String jIngredients = null;
        String jInstructions = null;
        short jPrepTime = -1;
        int jAuthor = -1;
        int jLikesNum = 0;
        Timestamp jUploadDate = null;
        boolean jAllergyTrigger = false;
        byte[] jImage = null;
        String jImageType = null;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"recipe".equals(jp.getCurrentName())) {
                if (jp.nextToken() == null) {
                    LOGGER.error("No Recipe object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no Recipe object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {
                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                    switch (jp.getCurrentName()) {
                        case "id":
                            jp.nextToken();
                            jId = jp.getIntValue();
                            break;
                        case "title":
                            jp.nextToken();
                            jTitle = jp.getText();
                            break;
                        case "desc":
                            jp.nextToken();
                            jDesc = jp.getText();
                            break;
                        case "ingredients":
                            jp.nextToken();
                            jIngredients = jp.getText();
                            break;
                        case "instructions":
                            jp.nextToken();
                            jInstructions = jp.getText();
                            break;
                        case "prep_time":
                            jp.nextToken();
                            jPrepTime = (short) jp.getIntValue();
                            break;
                        case "author":
                            jp.nextToken();
                            jAuthor = jp.getIntValue();
                            break;
                        case "likes_num":
                            jp.nextToken();
                            jLikesNum = jp.getIntValue();
                            break;
                        case "upload_date":
                            jp.nextToken();
                            jUploadDate = Timestamp.valueOf(jp.getText());
                            break;
                        case "allergy_trigger":
                            jp.nextToken();
                            jAllergyTrigger = jp.getBooleanValue();
                            break;
                        // Parse image and imageType fields if needed
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse a Recipe object from JSON.", e);
            throw e;
        }

        return new Recipe(jId, jTitle, jDesc, jIngredients, jInstructions, jPrepTime, jAuthor, jLikesNum,
                jUploadDate, jAllergyTrigger, jImage, jImageType);
    }
}
