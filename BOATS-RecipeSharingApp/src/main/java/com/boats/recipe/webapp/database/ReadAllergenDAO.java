package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Allergen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadAllergenDAO extends AbstractDAO<List<Allergen>> {

    private static final String SELECT_ALLERGEN_SQL = "SELECT name FROM recipe_platform_schema.Allergen";

    public ReadAllergenDAO(Connection con) {
        super(con);
    }

    @Override
    protected void doAccess() throws SQLException {
        List<Allergen> allergens = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(SELECT_ALLERGEN_SQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                allergens.add(new Allergen(name));
            }
        }
        outputParam = allergens;
    }
}
