package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Allergen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists all the allergens in the database.
 */

public final class ListAllergenDAO extends AbstractDAO<List<Allergen>> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, name FROM recipe_platform_schema.Allergen";

    /**
     * Creates a new object for listing all the allergens.
     *
     * @param con the connection to the database.
     */
    public ListAllergenDAO(final Connection con) {
        super(con);
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Allergen> allergens = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(STATEMENT);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                allergens.add(new Allergen(rs.getInt("id"), rs.getString("name")));
            }

            LOGGER.info("Allergen(s) successfully listed.");
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
