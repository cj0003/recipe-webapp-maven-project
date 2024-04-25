package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Recipe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists all the recipes in the database.
 */
public final class ListAllRecipesDAO extends AbstractDAO<List<Recipe>> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, title, \"desc\", ingredients, instructions, prep_time, author, likes_num, upload_date, allergy_trigger, image, image_type FROM recipe_platform_schema.Recipe";

    /**
     * Creates a new object for listing all the recipes.
     *
     * @param con the connection to the database.
     */
    public ListAllRecipesDAO(final Connection con) {
        super(con);
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Recipe> recipes = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(STATEMENT);

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

            LOGGER.info("Recipe(s) successfully listed.");
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
