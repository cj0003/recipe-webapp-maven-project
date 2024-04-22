package com.boats.recipe.webapp.database;


import com.boats.recipe.webapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates a new user into the database.
 */

public final class CreateUserDAO extends AbstractDAO<User> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO recipe_platform_schema.User (name, surname, email, password, username, bio, reg_date, image, image_type) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";

    /**
     * The user to be stored into the database
     */
    private final User user;

    /**
     * Creates a new object for storing a user into the database.
     *
     * @param con
     *            the connection to the database.
     * @param user
     *            the user to be stored into the database.
     */
    public CreateUserDAO(final Connection con, final User user) {
        super(con);

        if (user == null) {
            LOGGER.error("The user cannot be null.");
            throw new NullPointerException("The user cannot be null.");
        }

        this.user = user;
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the created user
        User createdUser = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getSurname());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getUsername());
            pstmt.setString(6, user.getBio());
            pstmt.setTimestamp(7, user.getRegistrationDate());
            pstmt.setBytes(8, user.getImage());
            pstmt.setString(9, user.getImageType());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                createdUser = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getString("bio"),
                        rs.getTimestamp("reg_date"),
                        rs.getBytes("image"),
                        rs.getString("image_type"));

                LOGGER.info("User with ID %d successfully stored in the database. {}", createdUser.getId());
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }

        outputParam = createdUser;
    }
}