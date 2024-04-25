package com.boats.recipe.webapp.rest;

import com.boats.recipe.webapp.database.UpdateRecipeDAO;
import com.boats.recipe.webapp.resources.Actions;
import com.boats.recipe.webapp.model.Recipe;
import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public final class UpdateRecipeRR extends AbstractRR {
    public UpdateRecipeRR(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(Actions.UPDATE_RECIPE, req, res, con);
    }
    @Override
    protected void doServe() throws IOException {
        Recipe r = null;
        Message m = null;

        try {
            // parse the URI path to extract the badge
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("recipe") + 6);

            final int badge = Integer.parseInt(path.substring(1));

            LogContext.setResource(Integer.toString(badge));

            final Recipe recipe = Recipe.fromJSON(req.getInputStream());

            if (badge != recipe.getId()) {
                LOGGER.warn("Cannot update the recipe: URI request (%d) and recipe resource (%d) badges differ.",
                        badge, recipe.getId());

                m = new Message("Cannot update the recipe: URI request and recipe resource badges differ.", "E4A8",
                        String.format("Request URI badge %d; recipe resource badge %d.", badge, recipe.getId()));
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                m.toJSON(res.getOutputStream());
                return;
            }


            // creates a new DAO for accessing the database and updates the recipe
            r = new UpdateRecipeDAO(con, recipe).access().getOutputParam();

            if (r != null) {
                LOGGER.info("recipe successfully updated.");

                res.setStatus(HttpServletResponse.SC_OK);
                r.toJSON(res.getOutputStream());
            } else {
                LOGGER.warn("recipe not found. Cannot update it.");

                m = new Message(String.format("recipe %d not found. Cannot update it.", badge), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }
        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
            LOGGER.warn("Cannot delete the recipe: wrong format for URI /recipe/{Id}.", ex);

            m = new Message("Cannot delete the recipe: wrong format for URI /recipe/{Id}.", "E4A7",
                    ex.getMessage());
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(res.getOutputStream());
        } catch (EOFException ex) {
            LOGGER.warn("Cannot updated the recipe: no Recipe JSON object found in the request.", ex);

            m = new Message("Cannot update the recipe: no Recipe JSON object found in the request.", "E4A8",
                    ex.getMessage());
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(res.getOutputStream());
        } catch (SQLException ex) {
            if ("23503".equals(ex.getSQLState())) {
                LOGGER.warn("Cannot delete the recipe: other resources depend on it.");

                m = new Message("Cannot delete the recipe: other resources depend on it.", "E5A4", ex.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            } else {
                LOGGER.error("Cannot delete the recipe: unexpected database error.", ex);

                m = new Message("Cannot delete the recipe: unexpected database error.", "E5A1", ex.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }
}
