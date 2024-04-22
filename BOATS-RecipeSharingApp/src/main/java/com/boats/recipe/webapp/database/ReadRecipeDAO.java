package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Recipe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reads a recipe from the database.
 */
public final class ReadRecipeDAO extends AbstractDAO<Recipe> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, title, \"desc\", ingredients, instructions, prep_time, author, likes_num, upload_date, allergy_trigger, image, image_type FROM recipe_platform_schema.Recipe WHERE id = ?";

    /**
     * The ID of the recipe
     */
    private final int recipeId;

    /**
     * Creates a new object for reading a recipe.
     *
     * @param con      the connection to the database.
     * @param recipeId the ID of the recipe.
     */
    public ReadRecipeDAO(final Connection con, final int recipeId) {
        super(con);
        this.recipeId = recipeId;
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the read recipe
        Recipe recipe = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, recipeId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                recipe = new Recipe(
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

                LOGGER.info("Recipe with ID %d successfully read from the database. {}", recipe.getId());
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        outputParam = recipe;
    }
}

