package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Deletes a user from the database.
 */
public final class DeleteUserDAO extends AbstractDAO<User> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM recipe_platform_schema.User WHERE id = ? RETURNING *";

    /**
     * The ID of the user
     */
    private final int id;

    /**
     * Creates a new object for deleting a user.
     *
     * @param con
     *            the connection to the database.
     * @param id
     *            the ID of the user.
     */
    public DeleteUserDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the deleted user
        User user = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
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

                LOGGER.info("User with ID %d successfully deleted from the database. {}", user.getId());
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        outputParam = user;
    }
}
