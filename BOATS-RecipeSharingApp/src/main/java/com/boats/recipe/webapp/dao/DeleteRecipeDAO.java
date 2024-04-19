package com.boats.recipe.webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecipeDAO extends AbstractDAO<Void> {

    private static final String DELETE_RECIPE_SQL = "DELETE FROM Recipe WHERE id=?";

    private final int recipeId;

    public DeleteRecipeDAO(Connection con, int recipeId) {
        super(con);
        this.recipeId = recipeId;
    }

    @Override
    protected void doAccess() throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(DELETE_RECIPE_SQL)) {
            pstmt.setInt(1, recipeId);
            pstmt.executeUpdate();
        }
    }
}
