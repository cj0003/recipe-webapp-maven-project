package com.boats.recipe.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateCollectionDAO extends AbstractDAO<Void> {

    private static final String INSERT_COLLECTION_SQL = "INSERT INTO Collection (title, description, creator) " +
            "VALUES (?, ?, ?)";

    private final String title;
    private final String description;
    private final int creator;

    public CreateCollectionDAO(Connection con, String title, String description, int creator) {
        super(con);
        this.title = title;
        this.description = description;
        this.creator = creator;
    }

    @Override
    protected void doAccess() throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_COLLECTION_SQL)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, creator);
            pstmt.executeUpdate();
        }
    }
}
