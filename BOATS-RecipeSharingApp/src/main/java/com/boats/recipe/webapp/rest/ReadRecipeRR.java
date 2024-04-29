package com.boats.recipe.webapp.rest;

import com.boats.recipe.webapp.database.ReadRecipeDAO;
import com.boats.recipe.webapp.model.Recipe;
import com.boats.recipe.webapp.resources.Actions;
import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.Message;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public final class ReadRecipeRR extends AbstractRR {

    public ReadRecipeRR(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(Actions.READ_RECIPE, req, res, con);
    }

    @Override
    protected void doServe() throws IOException {

        Recipe r = null;
        Message m = null;
        System.out.println("Outside try blocks");
        try {
            // parse the URI path to extract the ID
            String path = req.getRequestURI();
            System.out.println("Inside try getting path");
            path = path.substring(path.lastIndexOf("recipe") + 6);
            System.out.println(path);
            final int recipeId = Integer.parseInt(path.substring(1));
            System.out.println("log is here to read recipe from readrecipeRR--->");
            System.out.println(recipeId);
            LogContext.setResource(Integer.toString(recipeId));

            // Create a new DAO for accessing the database and read the Recipe
            ReadRecipeDAO readRecipeDAO = new ReadRecipeDAO(con, recipeId);
            readRecipeDAO.access();
            r = readRecipeDAO.getOutputParam();

            if (r != null) {
                LOGGER.info("Recipe successfully read.");

                res.setStatus(HttpServletResponse.SC_OK);
                r.toJSON(res.getOutputStream());
            } else {
                LOGGER.warn("Recipe not found. Cannot read it.");

                m = new Message(String.format("Recipe %d not found. Cannot read it.", recipeId), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }
        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
            LOGGER.warn("Cannot read the Recipe: wrong format for URI /recipe/{id}.", ex);

            m = new Message("Cannot read the Recipe: wrong format for URI /recipe/{id}.", "E4A7", ex.getMessage());
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(res.getOutputStream());
        } catch (SQLException ex) {
            LOGGER.error("Cannot read the Recipe: unexpected database error.", ex);

            m = new Message("Cannot read the Recipe: unexpected database error.", "E5A1", ex.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }
}
