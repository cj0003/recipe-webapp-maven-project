package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Recipe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists all the recipes that a user liked/saved in a specific collection in the database.
 */
public final class ListUserSavedRecipesInCollectionsDAO extends AbstractDAO<List<Recipe>> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT r.id, r.title, r.\"desc\", r.ingredients, r.instructions, r.prep_time, r.author, r.likes_num, r.upload_date, r.allergy_trigger, r.image, r.image_type " +
            "FROM recipe_platform_schema.recipe r " +
            "JOIN recipe_platform_schema.like usr ON r.id = usr.recipeID " +
            "JOIN recipe_platform_schema.membership m ON r.id = m.recipeID " +
            "WHERE usr.userID = ? AND m.collectionID = ?";

    /**
     * The user ID whose saved recipes will be listed
     */
    private final int userID;

    /**
     * The collection ID in which the recipes are saved
     */
    private final int collectionID;

    /**
     * Creates a new object for listing all the recipes that a user liked/saved in a specific collection.
     *
     * @param con         the connection to the database.
     * @param userID      the user ID whose saved recipes will be listed
     * @param collectionID the collection ID in which the recipes are saved
     */
    public ListUserSavedRecipesInCollectionsDAO(final Connection con, final int userID, final int collectionID) {
        super(con);
        this.userID = userID;
        this.collectionID = collectionID;
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Recipe> recipes = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, collectionID);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Recipe recipe = new Recipe(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("desc"),
                        rs.getString("ingredients"),
                        rs.getString("instructions"),
                        rs.getInt("prep_time"),
                        rs.getInt("author"),
                        rs.getInt("likes_num"),
                        rs.getTimestamp("upload_date"),
                        rs.getBoolean("allergy_trigger"),
                        rs.getBytes("image"),
                        rs.getString("image_type")
                );
                recipes.add(recipe);
            }

            LOGGER.info("User's saved recipe(s) in collection successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

        }

        outputParam = recipes;
    }
}
