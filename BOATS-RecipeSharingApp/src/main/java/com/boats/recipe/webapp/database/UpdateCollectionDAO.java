package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Collection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Updates a collection in the database.
 */
public final class UpdateCollectionDAO extends AbstractDAO<Collection> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "UPDATE recipe_platform_schema.Collection SET title = ?, \"desc\" = ?, creator = ? WHERE id = ? RETURNING *";

    /**
     * The collection to be updated in the database
     */
    private final Collection collection;

    /**
     * Creates a new object for updating a collection.
     *
     * @param con
     *            the connection to the database.
     * @param collection
     *            the collection to be updated in the database.
     */
    public UpdateCollectionDAO(final Connection con, final Collection collection) {
        super(con);

        if (collection == null) {
            LOGGER.error("The collection cannot be null.");
            throw new NullPointerException("The collection cannot be null.");
        }

        this.collection = collection;
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the updated collection
        Collection updatedCollection = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, collection.getTitle());
            pstmt.setString(2, collection.getDescription());
            pstmt.setInt(3, collection.getCreator());
            pstmt.setInt(4, collection.getId());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                updatedCollection = new Collection(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("desc"),
                        rs.getInt("creator")
                );

                LOGGER.info("Collection %d successfully updated in the database. {}", updatedCollection.getId());
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        outputParam = updatedCollection;
    }
}
