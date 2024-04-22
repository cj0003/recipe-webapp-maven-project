package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Updates a user in the database.
 */
public final class UpdateUserDAO extends AbstractDAO<User> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE recipe_platform_schema.User SET name = ?, surname = ?, email = ?, password = ?, username = ?, bio = ?, reg_date = ?, image = ?, image_type = ? WHERE id = ? RETURNING *";

    /**
     * The user to be updated in the database
     */
    private final User user;

    /**
     * Creates a new object for updating a user.
     *
     * @param con
     *            the connection to the database.
     * @param user
     *            the user to be updated in the database.
     */
    public UpdateUserDAO(final Connection con, final User user) {
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

        // the updated user
        User updatedUser = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getSurname());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getUsername());
            pstmt.setString(6, user.getBio());
            pstmt.setObject(7, user.getRegistrationDate());
            pstmt.setBytes(8, user.getImage());
            pstmt.setString(9, user.getImageType());
            pstmt.setInt(10, user.getId());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                updatedUser = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getString("bio"),
                        rs.getTimestamp("reg_date"),
                        rs.getBytes("image"),
                        rs.getString("image_type")
                );

                LOGGER.info("User %d successfully updated in the database. {}", updatedUser.getId());
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        outputParam = updatedUser;
    }
}
