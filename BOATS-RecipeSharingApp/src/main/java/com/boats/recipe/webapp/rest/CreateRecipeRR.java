package com.boats.recipe.webapp.rest;

import com.boats.recipe.webapp.database.CreateRecipeDAO;
import com.boats.recipe.webapp.resources.Actions;
import com.boats.recipe.webapp.model.Recipe;
import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.EOFException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public final class CreateRecipeRR extends AbstractRR {
    public CreateRecipeRR(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(Actions.CREATE_RECIPE, req, res, con);
    }

    @Override
    protected void doServe() throws IOException {

        Recipe r = null;
        Message m = null;

        try {
            LOGGER.warn("Inside try now I am trying");
                final Recipe recipe = Recipe.fromJSON(req.getInputStream());
            LOGGER.warn("Final recipe object: {}", recipe.toString());

            LogContext.setResource(Integer.toString(recipe.getId()));

            // creates a new DAO for accessing the database and stores the recipe
            r = new CreateRecipeDAO(con, recipe).access().getOutputParam();

            if (r != null) {
                LOGGER.info("Recipe successfully created.");

                res.setStatus(HttpServletResponse.SC_CREATED);
                r.toJSON(res.getOutputStream());
            } else { // it should not happen
                LOGGER.error("Fatal error while creating Recipe.");

                m = new Message("Cannot create the employee: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (EOFException ex) {
            LOGGER.warn("Cannot create the Recipe: no Recipe JSON object found in the request.", ex);

            m = new Message("Cannot create the Recipe: no Recipe JSON object found in the request.", "E4A8",
                    ex.getMessage());
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(res.getOutputStream());
        } catch (SQLException ex) {
            if ("23505".equals(ex.getSQLState())) {
                LOGGER.warn("Cannot create the Recipe: it already exists.");

                m = new Message("Cannot create the Recipe: it already exists.", "E5A2", ex.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            } else {
                LOGGER.error("Cannot create the Recipe: unexpected database error.", ex);

                m = new Message("Cannot create the Recipe: unexpected database error.", "E5A1", ex.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }
}
