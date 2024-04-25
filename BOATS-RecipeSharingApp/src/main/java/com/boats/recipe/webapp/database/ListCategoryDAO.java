package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Category; // Make sure this import statement is present

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lists all the categories in the database.
 */

public final class ListCategoryDAO extends AbstractDAO<List<Category>> {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, name FROM recipe_platform_schema.Category";

    /**
     * Creates a new object for listing all the categories.
     *
     * @param con the connection to the database.
     */
    public ListCategoryDAO(final Connection con) {
        super(con);
    }

    @Override
    protected final void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Category> categories = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(STATEMENT);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }

            LOGGER.info("Category(s) successfully listed.");
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        outputParam = categories;
    }
}
