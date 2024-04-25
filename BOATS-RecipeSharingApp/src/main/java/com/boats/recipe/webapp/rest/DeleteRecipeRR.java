package com.boats.recipe.webapp.rest;

import com.boats.recipe.webapp.database.DeleteRecipeDAO;
import com.boats.recipe.webapp.database.ReadRecipeDAO;
import com.boats.recipe.webapp.model.Recipe;
import com.boats.recipe.webapp.resources.Actions;
import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public final class DeleteRecipeRR extends AbstractRR {

    public DeleteRecipeRR(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(Actions.DELETE_RECIPE, req, res, con);
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

            // creates a new DAO for accessing the database and deletes the Recipe
            r = new DeleteRecipeDAO(con, badge).access().getOutputParam();

            if (r != null) {
                LOGGER.info("Recipe successfully deleted.");

                res.setStatus(HttpServletResponse.SC_OK);
                r.toJSON(res.getOutputStream());
            } else {
                LOGGER.warn("Recipe not found. Cannot delete it.");

                m = new Message(String.format("Recipe %d not found. Cannot delete it.", badge), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }
        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
            LOGGER.warn("Cannot delete the Recipe: wrong format for URI /Recipe/{badge}.", ex);

            m = new Message("Cannot delete the Recipe: wrong format for URI /Recipe/{badge}.", "E4A7",
                    ex.getMessage());
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(res.getOutputStream());
        } catch (SQLException ex) {
            if ("23503".equals(ex.getSQLState())) {
                LOGGER.warn("Cannot delete the Recipe: other resources depend on it.");

                m = new Message("Cannot delete the Recipe: other resources depend on it.", "E5A4", ex.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            } else {
                LOGGER.error("Cannot delete the Recipe: unexpected database error.", ex);

                m = new Message("Cannot delete the Recipe: unexpected database error.", "E5A1", ex.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }
}
