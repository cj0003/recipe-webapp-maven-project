package com.boats.recipe.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteCollectionDAO extends AbstractDAO<Void> {

    private static final String DELETE_COLLECTION_SQL = "DELETE FROM Collection WHERE id = ?";

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
