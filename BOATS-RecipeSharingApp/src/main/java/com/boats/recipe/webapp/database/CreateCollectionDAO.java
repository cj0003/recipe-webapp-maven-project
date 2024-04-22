package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Collection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates a new collection into the database.
 */

public final class CreateCollectionDAO extends AbstractDAO<Collection> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO recipe_platform_schema.Collection (title, \"desc\", creator) VALUES (?, ?, ?) RETURNING *";

    /**
     * The collection to be stored into the database
     */
    private final Collection collection;

    /**
     * Creates a new object for storing a collection into the database.
     *
     * @param con
     *            the connection to the database.
     * @param collection
     *            the collection to be stored into the database.
     */
    public CreateCollectionDAO(final Connection con, final Collection collection) {
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

        // the created collection
        Collection createdCollection = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, collection.getTitle());
            pstmt.setString(2, collection.getDescription());
            pstmt.setInt(3, collection.getCreator());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                createdCollection = new Collection(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("desc"),
                        rs.getInt("creator"));

                LOGGER.info("Collection with ID %d successfully stored in the database. {}", createdCollection.getId());
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }

        outputParam = createdCollection;
    }
}
