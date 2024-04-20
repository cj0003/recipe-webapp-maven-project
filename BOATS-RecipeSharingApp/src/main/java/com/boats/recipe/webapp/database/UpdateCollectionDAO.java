package com.boats.recipe.webapp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCollectionDAO extends AbstractDAO<Void> {

    private static final String UPDATE_COLLECTION_SQL = "UPDATE recipe_platform_schema.Collection SET title = ?, description = ? WHERE id = ?";

    private final int collectionId;
    private final String newTitle;
    private final String newDescription;

    public UpdateCollectionDAO(Connection con, int collectionId, String newTitle, String newDescription) {
        super(con);
        this.collectionId = collectionId;
        this.newTitle = newTitle;
        this.newDescription = newDescription;
    }

    @Override
    protected void doAccess() throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(UPDATE_COLLECTION_SQL)) {
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newDescription);
            pstmt.setInt(3, collectionId);
            pstmt.executeUpdate();
        }
    }
}
