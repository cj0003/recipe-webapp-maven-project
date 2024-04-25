package com.boats.recipe.webapp.rest;

import com.boats.recipe.webapp.database.ListAllRecipesDAO;
import com.boats.recipe.webapp.resources.Actions;
import com.boats.recipe.webapp.model.Recipe;
import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.Message;
import com.boats.recipe.webapp.resources.ResourceList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.EOFException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListRecipeRR extends AbstractRR{
    public ListRecipeRR(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(Actions.LIST_RECIPES, req, res, con);
    }

    @Override
    protected void doServe() throws IOException {

        List<Recipe> re = null;
        Message m = null;

        try {

            // creates a new DAO for accessing the database and lists the employee(s)
            re = new ListAllRecipesDAO(con).access().getOutputParam();

            if (re != null) {
                LOGGER.info("Recipe(s) successfully listed.");

                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(re).toJSON(res.getOutputStream());
            } else { // it should not happen
                LOGGER.error("Fatal error while listing Recipe(s).");

                m = new Message("Cannot list Recipe(s): unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot list Recipe(s): unexpected database error.", ex);

            m = new Message("Cannot list Recipe(s): unexpected database error.", "E5A1", ex.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }
}
