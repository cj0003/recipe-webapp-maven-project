package com.boats.recipe.webapp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateUserDAO extends AbstractDAO<Void> {

    private static final String SIGNUP_QUERY = "INSERT INTO recipe_platform_schema.User (username, password, name, surname, bio, email, registration_date) VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

    private final String username;
    private final String password;
    private final String name;
    private final String surname;
    private final String bio;
    private final String email;

    public CreateUserDAO(Connection connection, String username, String password, String name, String surname, String bio, String email) {
        super(connection);
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.bio = bio;
        this.email = email;
    }

    @Override
    protected void doAccess() throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SIGNUP_QUERY)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, surname);
            pstmt.setString(5, bio);
            pstmt.setString(6, email);
            pstmt.executeUpdate();

            LOGGER.info("Signup successful for user: {}", username);
        }
    }
}
