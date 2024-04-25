package com.boats.recipe.webapp.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.io.OutputStream; // Add this import for OutputStream
import java.io.EOFException; // Add this import for EOFException

public class User extends AbstractResource {

    private final int id;
    private final String name;
    private final String surname;
    private final String email;
    private final String password;
    private final String username;
    private final String bio;
    private final Time regDate;
    private final byte[] image;
    private final String imageType;

    public User(final int id, final String name, final String surname, final String email, final String password, final String username, final String bio,
                final Time regDate, final byte[] image, final String imageType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.username = username;
        this.bio = bio;
        this.regDate = regDate;
        this.image = image;
        this.imageType = imageType;
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

    public final String getEmail() {
        return email;
    }

    public final String getPassword() {
        return password;
    }

    public final String getUsername() {
        return username;
    }

    public final String getBio() {
        return bio;
    }

    public final Time getRegDate() {
        return regDate;
    }

    public final byte[] getImage() {
        return image;
    }

    public final String getImageType() {
        return imageType;
    }

    // Override the writeJSON method to write User object to JSON
    @Override
    protected final void writeJSON(final OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);
        jg.writeStartObject();
        jg.writeFieldName("user");
        jg.writeStartObject();
        jg.writeNumberField("id", id);
        jg.writeStringField("name", name);
        jg.writeStringField("surname", surname);
        jg.writeStringField("email", email);
        jg.writeStringField("password", password);
        jg.writeStringField("username", username);
        jg.writeStringField("bio", bio);
        // Assuming regDate is already in a suitable format for JSON
        jg.writeStringField("regDate", regDate.toString());
        // If needed, add fields for image and imageType
        jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
    }


    // Add method to create User object from JSON
    public static User fromJSON(final InputStream in) throws IOException {
        int jId = -1;
        String jName = null;
        String jSurname = null;
        String jEmail = null;
        String jPassword = null;
        String jUsername = null;
        String jBio = null;
        Time jRegDate = null;
        byte[] jImage = null;
        String jImageType = null;

        try {
            final JsonParser jp = JSON_FACTORY.createParser(in);

            while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"user".equals(jp.getCurrentName())) {
                // If there are no more events, it means no User object was found
                if (jp.nextToken() == null) {
                    LOGGER.error("No User object found in the stream.");
                    throw new EOFException("Unable to parse JSON: no User object found.");
                }
            }

            while (jp.nextToken() != JsonToken.END_OBJECT) {
                if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                    switch (jp.getCurrentName()) {
                        case "id":
                            jp.nextToken();
                            jId = jp.getIntValue();
                            break;
                        case "name":
                            jp.nextToken();
                            jName = jp.getText();
                            break;
                        case "surname":
                            jp.nextToken();
                            jSurname = jp.getText();
                            break;
                        case "email":
                            jp.nextToken();
                            jEmail = jp.getText();
                            break;
                        case "password":
                            jp.nextToken();
                            jPassword = jp.getText();
                            break;
                        case "username":
                            jp.nextToken();
                            jUsername = jp.getText();
                            break;
                        case "bio":
                            jp.nextToken();
                            jBio = jp.getText();
                            break;
                        case "regDate":
                            jp.nextToken();
                            // Convert string to Time if needed
                            jRegDate = Time.valueOf(jp.getText());
                            break;
                        // Parse image and imageType fields if needed

                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse a User object from JSON.", e);
            throw e;
        }

        return new User(jId, jName, jSurname, jEmail, jPassword, jUsername, jBio, jRegDate, jImage, jImageType);
    }
}

