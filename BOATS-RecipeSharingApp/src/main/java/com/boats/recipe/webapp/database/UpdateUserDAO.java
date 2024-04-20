package com.boats.recipe.webapp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateUserDAO extends AbstractDAO<Void> {

    private static final String UPDATE_USER_QUERY = "UPDATE recipe_platform_schema.User SET username = ?, password = ?, name = ?, surname = ?, bio = ?, email = ? WHERE id = ?";

    private final int userId;
    private final String username;
    private final String password;
    private final String name;
    private final String surname;
    private final String bio;
    private final String email;

    public UpdateUserDAO(Connection connection, int userId, String username, String password, String name, String surname, String bio, String email) {
        super(connection);
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.bio = bio;
        this.email = email;
    }

    @Override
    protected void doAccess() throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_USER_QUERY)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, surname);
            pstmt.setString(5, bio);
            pstmt.setString(6, email);
            pstmt.setInt(7, userId);
            pstmt.executeUpdate();

            LOGGER.info("User information updated for user ID: {}", userId);
        }
    }
}
