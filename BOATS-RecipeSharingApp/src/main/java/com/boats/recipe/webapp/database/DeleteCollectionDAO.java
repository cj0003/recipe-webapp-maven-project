package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Deletes a collection from the database.
 */
public final class DeleteCollectionDAO extends AbstractDAO<Collection> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM recipe_platform_schema.Collection WHERE id = ? RETURNING *";

    /**
     * The ID of the collection
     */
    private final int id;

    /**
     * Creates a new object for deleting a collection.
     *
     * @param con
     *            the connection to the database.
     * @param id
     *            the ID of the collection.
     */
    public DeleteCollectionDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the deleted collection
        Collection collection = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                collection = new Collection(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("desc"),
                        rs.getInt("creator"));

                LOGGER.info("Collection with ID %d successfully deleted from the database. {}", collection.getId());
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        outputParam = collection;
    }
}
