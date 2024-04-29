package com.boats.recipe.webapp.rest;

import com.boats.recipe.webapp.database.ReadUserDAO;
import com.boats.recipe.webapp.resources.Actions;
import com.boats.recipe.webapp.model.User;
import com.boats.recipe.webapp.resources.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ReadUserRR extends AbstractRR {
    public ReadUserRR(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(Actions.READ_USER, req, res, con);
    }
        public void doServe() throws IOException {
            User us = null;
            Message m = null;
           String username = req.getParameter("email");
         String password = req.getParameter("password");
            try{
                //create a dao for accessing the  of users
                us = new ReadUserDAO(con,username,password).access().getOutputParam();
                if(us != null){
                    LOGGER.info("users found");
                    res.setStatus(HttpServletResponse.SC_OK);
                    us.toJSON(res.getOutputStream());
                }
                else { // it should not happen
                    LOGGER.error("Fatal error while listing User(s).");

                    m = new Message("Cannot list User(s): unexpected error.", "E5A1", null);
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    m.toJSON(res.getOutputStream());
                }
            }
            catch(SQLException e){
                System.out.println(e);
                LOGGER.error("Cannot list User(s): unexpected database error.", e);

                m = new Message("Cannot list User(s): unexpected database error.", "E5A1", e.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
    }
}
