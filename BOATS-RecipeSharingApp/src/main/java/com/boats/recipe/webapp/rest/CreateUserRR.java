package com.boats.recipe.webapp.rest;

import com.boats.recipe.webapp.database.CreateRecipeDAO;
import com.boats.recipe.webapp.database.CreateUserDAO;
import com.boats.recipe.webapp.resources.Actions;
import com.boats.recipe.webapp.resources.User;
import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.EOFException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public final class CreateUserRR extends AbstractRR {

    public CreateUserRR(final HttpServletRequest req, final HttpServletResponse res, final Connection con) {
        super(Actions.CREATE_USER, req, res, con);
    }

    @Override
    public void doServe() throws IOException {
        // Extract user signup data from the request body
        // Validate the input data (e.g., check for required fields)
        // Create a new user account in the database
        // Return a success message or error message based on the result
        // Example implementation:
        // UserSignupService service = new UserSignupService();
        // boolean success = service.signup(req.getParameter("username"), req.getParameter("email"), req.getParameter("password"));
        // if (success) {
        //     res.setStatus(HttpServletResponse.SC_CREATED);
        //     new Message("User account created successfully.").toJSON(res.getOutputStream());
        // } else {
        //     res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        //     new Message("Failed to create user account.").toJSON(res.getOutputStream());
        // }

        User u = null;
        Message m = null;
        try {
            LOGGER.warn("Inside the try of CreateUserRR.doServe");
            final User user = User.fromJSON(req.getInputStream());
            LOGGER.warn("Final user obtained:{}", user.toString());

            LogContext.setResource(Integer.toString(user.getId()));

            // creates a new DAO for accessing the database and stores the user
            u = new CreateUserDAO(con, user).access().getOutputParam();

            if (u != null) {
                LOGGER.info("User successfully added.");

                res.setStatus(HttpServletResponse.SC_CREATED);
                u.toJSON(res.getOutputStream());
            } else {
                // it should not happen
                LOGGER.error("Error encountered while creating User.");

                m = new Message("Cannot create the employee: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }

        } catch (EOFException ex) {
            LOGGER.warn("Cannot create the User: no User JSON object found in the request.", ex);

            m = new Message("Cannot create the User: no User JSON object found in the request.", "E4A8",
                    ex.getMessage());
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(res.getOutputStream());
        } catch (SQLException sq) {
            if ("23505".equals(sq.getSQLState())) {
                LOGGER.warn("Cannot create the User: already exists.");

                m = new Message("Cannot create the User: already exists.", "E5A2", sq.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            } else {
                LOGGER.error("Cannot create the User: unexpected database error.", sq);

                m = new Message("Cannot create the User: unexpected database error.", "E5A1", sq.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }

    }
}
//all necessary changes done
