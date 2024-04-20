package com.boats.recipe.webapp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteCollectionDAO extends AbstractDAO<Void> {

    private static final String DELETE_COLLECTION_SQL = "DELETE FROM recipe_platform_schema.Collection WHERE id = ?";

    private final int collectionId;

    public DeleteCollectionDAO(Connection con, int collectionId) {
        super(con);
        this.collectionId = collectionId;
    }

    @Override
    protected void doAccess() throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_COLLECTION_SQL)) {
            pstmt.setInt(1, collectionId);
            pstmt.executeUpdate();
        }
    }
}
