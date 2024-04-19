package com.boats.recipe.webapp.dao;
import com.boats.recipe.webapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LoginDAO extends AbstractDAO<User> {

    private static final String LOGIN_QUERY = "SELECT * FROM User WHERE username = ? AND password = ?";

    private final String username;
    private final String password;

    public LoginDAO(Connection connection, String username, String password) {
        super(connection);
        this.username = username;
        this.password = password;
    }

    @Override
    protected void doAccess() throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(LOGIN_QUERY)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    outputParam = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("bio"),
                            rs.getString("email"),
                            rs.getTimestamp("registration_date"),
                            rs.getBytes("image"),
                            rs.getString("image_type")
                    );
                    LOGGER.info("Login successful for user: {}", username);
                } else {
                    LOGGER.info("Login failed for user: {}", username);
                }
            }
        }
    }
}
