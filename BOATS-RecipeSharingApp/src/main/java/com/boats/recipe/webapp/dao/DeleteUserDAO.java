package com.boats.recipe.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteUserDAO extends AbstractDAO<Void> {

    private static final String DELETE_USER_QUERY = "DELETE FROM User WHERE id = ?";

    private final int userId;

    public DeleteUserDAO(Connection connection, int userId) {
        super(connection);
        this.userId = userId;
    }

    @Override
    protected void doAccess() throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_USER_QUERY)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();

            LOGGER.info("User deleted with ID: {}", userId);
        }
    }
}
