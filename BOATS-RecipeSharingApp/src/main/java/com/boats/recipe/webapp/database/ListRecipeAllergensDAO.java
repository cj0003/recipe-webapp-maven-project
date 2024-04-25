package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Allergen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists all the allergens in a recipe in the database.
 */
public final class ListRecipeAllergensDAO extends AbstractDAO<List<Allergen>> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT a.id, a.name " +
            "FROM recipe_platform_schema.allergen a " +
            "JOIN recipe_platform_schema.use u ON a.id = u.allergenID " +
            "WHERE u.recipeID = ?";

    /**
     * The recipe ID whose allergens will be listed
     */
    private final int recipeID;

    /**
     * Creates a new object for listing all the allergens in a recipe.
     *
     * @param con      the connection to the database.
     * @param recipeID the recipe ID whose allergens will be listed
     */
    public ListRecipeAllergensDAO(final Connection con, final int recipeID) {
        super(con);
        this.recipeID = recipeID;
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Allergen> allergens = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, recipeID);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Allergen allergen = new Allergen(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                allergens.add(allergen);
            }

            LOGGER.info("Recipe's allergen(s) successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }
        outputParam = allergens;
    }
}
