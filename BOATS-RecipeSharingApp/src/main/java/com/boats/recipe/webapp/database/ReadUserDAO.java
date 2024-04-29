package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.resources.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reads a user from the database.
 */
public final class ReadUserDAO extends AbstractDAO<User> {

//    private static final String STATEMENT = "SELECT * FROM recipe_platform_schema.user WHERE username = ? AND password = ?";

    private static final String STATEMENT = "SELECT * FROM user WHERE id = ?";
//    private final String username;
//    private final String password;

    private final int userid;
//    public ReadUserDAO(final Connection connection, final String username, final String password) {
public ReadUserDAO(final Connection connection, final int userid) {
        super(connection);
//        this.username = username;
//        this.password = password;
    this.userid = userid;
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the read user
        User u = null;

        try  {
            pstmt = con.prepareStatement(STATEMENT);

//            pstmt.setString(1, username);
//            pstmt.setString(2, password);
            pstmt.setInt(1,userid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                u = new User(
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
//                LOGGER.info("Login successful for user: {}", username);

                LOGGER.info("Login successful for user: {}", u.getId());
            } else {
//                LOGGER.info("Login failed for user: {}", username);

                LOGGER.info("Login failed for user: {}", u.getId());
            }}
        finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }
        outputParam = u;
    }
}
