package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates a new recipe into the database.
 */
public final class CreateRecipeDAO extends AbstractDAO<Recipe> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO recipe_platform_schema.Recipe (title, \"desc\", ingredients, instructions, prep_time, author, likes_num, upload_date, allergy_trigger, image, image_type) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";

    /**
     * The recipe to be stored into the database
     */
    private final Recipe recipe;

    /**
     * Creates a new object for storing a recipe into the database.
     *
     * @param con
     *            the connection to the database.
     * @param recipe
     *            the recipe to be stored into the database.
     */
    public CreateRecipeDAO(final Connection con, final Recipe recipe) {
        super(con);

        if (recipe == null) {
            LOGGER.error("The recipe cannot be null.");
            throw new NullPointerException("The recipe cannot be null.");
        }

        this.recipe = recipe;
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the created recipe
        Recipe createdRecipe = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, recipe.getTitle());
            pstmt.setString(2, recipe.getDescription());
            pstmt.setString(3, recipe.getIngredients());
            pstmt.setString(4, recipe.getInstructions());
            pstmt.setInt(5, recipe.getPreparationTime());
            pstmt.setInt(6, recipe.getAuthor());
            pstmt.setInt(7, recipe.getNumberOfLikes());
            pstmt.setTimestamp(8, recipe.getUploadDate());
            pstmt.setBoolean(9, recipe.isAllergyTrigger());
            pstmt.setBytes(10, recipe.getImage());
            pstmt.setString(11, recipe.getImageType());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                createdRecipe = new Recipe(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("desc"),
                        rs.getString("ingredients"),
                        rs.getString("instructions"),
                        rs.getString("method"),
                        rs.getInt("prep_time"),
                        rs.getInt("author"),
                        rs.getInt("likes_num"),
                        rs.getTimestamp("upload_date"),
                        rs.getBoolean("allergy_trigger"),
                        rs.getBytes("image"),
                        rs.getString("image_type"));

                LOGGER.info("Recipe with ID %d successfully stored in the database.", createdRecipe.getId());
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }

        outputParam = createdRecipe;
    }
}
