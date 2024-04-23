package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Deletes a recipe from the database.
 */
public final class DeleteRecipeDAO extends AbstractDAO<Recipe> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM recipe_platform_schema.Recipe WHERE id = ? RETURNING *";

    /**
     * The ID of the recipe
     */
    private final int id;

    /**
     * Creates a new object for deleting a recipe.
     *
     * @param con
     *            the connection to the database.
     * @param id
     *            the ID of the recipe.
     */
    public DeleteRecipeDAO(final Connection con, final int id) {
        super(con);
        this.id = id;
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the deleted recipe
        Recipe recipe = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, id);

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
                        rs.getString("image_type"));

                LOGGER.info("Recipe with ID %d successfully deleted from the database. {}", recipe.getId());
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
