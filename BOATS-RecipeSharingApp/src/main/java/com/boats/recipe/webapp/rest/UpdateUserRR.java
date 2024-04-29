package com.boats.recipe.webapp.rest;

import com.boats.recipe.webapp.database.UpdateUserDAO;
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

public final class UpdateUserRR extends AbstractRR {

    public UpdateUserRR(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(Actions.UPDATE_USER, req, res, con);
    }
    @Override
        protected void doServe () throws IOException {
            User r = null;
            Message m = null;

            try {
                // parse the URI path to extract the badge
                String path = req.getRequestURI();
                path = path.substring(path.lastIndexOf("user") + 4);

                final int badge = Integer.parseInt(path.substring(1));

                LogContext.setResource(Integer.toString(badge));

                final User user = User.fromJSON(req.getInputStream());



                if (badge != user.getId()) {
                    LOGGER.warn("Cannot update the user: URI request (%d) and user resource (%d) badges differ.",badge, user.getId());

                    m = new Message("Cannot update the user: URI request and user resource badges differ.", "E4A8",
                            String.format("Request URI badge %d; usr resource badge %d.", badge, user.getId()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(res.getOutputStream());
                    return;
                }

                // creates a new DAO for accessing the database and updates the recipe
                r = new UpdateUserDAO(con, user).access().getOutputParam();

                if (r != null) {
                    LOGGER.info("user successfully updated.");

                    res.setStatus(HttpServletResponse.SC_OK);
                    r.toJSON(res.getOutputStream());
                } else {
                    LOGGER.warn("user not found. Cannot update it.");

                    m = new Message(String.format("user %d not found. Cannot update it.", badge), "E5A3", null);
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    m.toJSON(res.getOutputStream());
                }
            } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                LOGGER.warn("Cannot delete the recipe: wrong format for URI /user/{Id}.", ex);

                m = new Message("Cannot delete the recipe: wrong format for URI /user/{Id}.", "E4A7",
                        ex.getMessage());
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                m.toJSON(res.getOutputStream());
            } catch (EOFException ex) {
                LOGGER.warn("Cannot updated the user: no User JSON object found in the request.", ex);

                m = new Message("Cannot update the User: no User JSON object found in the request.", "E4A8",
                        ex.getMessage());
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                m.toJSON(res.getOutputStream());
            } catch (SQLException ex) {
                if ("23503".equals(ex.getSQLState())) {
                    LOGGER.warn("Cannot delete the user: other resources depend on it.");

                    m = new Message("Cannot delete the user: other resources depend on it.", "E5A4", ex.getMessage());
                    res.setStatus(HttpServletResponse.SC_CONFLICT);
                    m.toJSON(res.getOutputStream());
                } else {
                    LOGGER.error("Cannot delete the user: unexpected database error.", ex);

                    m = new Message("Cannot use the user: unexpected database error.", "E5A1", ex.getMessage());
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    m.toJSON(res.getOutputStream());
                }
            }
        }
    }

