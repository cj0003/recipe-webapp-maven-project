package com.boats.recipe.webapp.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.io.OutputStream;
import java.io.EOFException;

public class Recipe extends AbstractResource {

    private final int id;
    private final String title;
    private final String desc;
    private final String ingredients;
    private final String instructions;
    private final short prepTime;
    private final int author;
    private final int likesNum;
    private final Time uploadDate;
    private final boolean allergyTrigger;
    private final byte[] image;
    private final String imageType;

    public Recipe(final int id, final String title, final String desc, final String ingredients, final String instructions,
                  final short prepTime, final int author, final int likesNum, final Time uploadDate,
                  final boolean allergyTrigger, final byte[] image, final String imageType) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.prepTime = prepTime;
        this.author = author;
        this.likesNum = likesNum;
        this.uploadDate = uploadDate;
        this.allergyTrigger = allergyTrigger;
        this.image = image;
        this.imageType = imageType;
    }

    public final int getId() {
        return id;
    }

    public final String getTitle() {
        return title;
    }

    public final String getDesc() {
        return desc;
    }

    public final String getIngredients() {
        return ingredients;
    }

    public final String getInstructions() {
        return instructions;
    }

    public final short getPrepTime() {
        return prepTime;
    }

    public final int getAuthor() {
        return author;
    }

    public final int getLikesNum() {
        return likesNum;
    }

    public final Time getUploadDate() {
        return uploadDate;
    }

    public final boolean isAllergyTrigger() {
        return allergyTrigger;
    }

    public final byte[] getImage() {
        return image;
    }

    public final String getImageType() {
        return imageType;
    }

    @Override
    protected final void writeJSON(final OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeFieldName("recipe");
        jg.writeStartObject();
        jg.writeNumberField("id", id);
        jg.writeStringField("title", title);
        jg.writeStringField("desc", desc);
        jg.writeStringField("ingredients", ingredients);
        jg.writeStringField("instructions", instructions);
        jg.writeNumberField("prep_time", prepTime);
        jg.writeNumberField("author", author);
        jg.writeNumberField("likes_num", likesNum);
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
        Time jUploadDate = null;
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
                            jUploadDate = Time.valueOf(jp.getText());
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
