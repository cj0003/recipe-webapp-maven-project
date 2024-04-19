package com.boats.recipe.webapp.dao;

import com.boats.recipe.webapp.model.Category; // Make sure this import statement is present

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadCategoryDAO extends AbstractDAO<List<Category>> {

    private static final String SELECT_ALL_CATEGORIES_SQL = "SELECT id, name FROM Category";

    public ReadCategoryDAO(Connection con) {
        super(con);
    }

    @Override
    protected void doAccess() throws SQLException {
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(SELECT_ALL_CATEGORIES_SQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                categories.add(new Category(id, name));
            }
        }
        outputParam = categories;
    }
}
