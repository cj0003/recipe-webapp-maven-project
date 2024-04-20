package com.boats.recipe.webapp.database;

import com.boats.recipe.webapp.model.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateRecipeDAO extends AbstractDAO<Void> {

    private static final String INSERT_RECIPE_SQL = "INSERT INTO recipe_platform_schema.Recipe (title, description, ingredients, method, prep_time, author, number_of_likes, upload_date, allergy_trigger, image, image_type) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final Recipe recipe;

    public CreateRecipeDAO(Connection con, Recipe recipe) {
        super(con);
        this.recipe = recipe;
    }

    @Override
    protected void doAccess() throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(INSERT_RECIPE_SQL)) {
            pstmt.setString(1, recipe.getTitle());
            pstmt.setString(2, recipe.getDescription());
            pstmt.setString(3, recipe.getIngredients());
            pstmt.setString(4, recipe.getMethod());
            pstmt.setInt(5, recipe.getPreparationTime());
            pstmt.setInt(6, recipe.getAuthor());
            pstmt.setInt(7, recipe.getNumberOfLikes());
            pstmt.setTimestamp(8, recipe.getUploadDate());
            pstmt.setBoolean(9, recipe.isAllergyTrigger());
            pstmt.setBytes(10, recipe.getImage());
            pstmt.setString(11, recipe.getImageType());

            pstmt.executeUpdate();
        }
    }
}
