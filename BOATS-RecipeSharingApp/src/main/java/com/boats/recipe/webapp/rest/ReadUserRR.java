package com.boats.recipe.webapp.rest;

import com.boats.recipe.webapp.database.ReadUserDAO;
import com.boats.recipe.webapp.resources.Actions;
import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.User;
import com.boats.recipe.webapp.resources.Message;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
//           String username = req.getParameter("email");
//         String password = req.getParameter("password");
//            String id = Integer.parseInt(req.getParameter("id"));
//
//            final int userid= Integer.parseInt(path.substring(1));
            try{
                //create a dao for accessing the  of users
//                us = new ReadUserDAO(con,username,password).access().getOutputParam();
//                path path
//                String path = req.getRequestURI();
//                System.out.println("Inside try getting path");
//                path = path.substring(path.lastIndexOf("/rest/user") + 4);
//                System.out.println(path);
//                final int userId = Integer.parseInt(path.substring(1));
//                System.out.println("log is here to read user from readuserRR--->");
//                System.out.println(userId);
//                LogContext.setResource(Integer.toString(userId));

                String path = req.getRequestURI();
                System.out.println("Inside try getting path");
                String[] pathParts = path.split("/");
                int userIdIndex = -1;
                int userId =2;
// Find the index of "user" in the path
                for (int i = 0; i < pathParts.length; i++) {
                    if (pathParts[i].equals("user")) {
                        userIdIndex = i + 1; // The user ID follows "user"
                        break;
                    }
                }

                if (userIdIndex != -1 && userIdIndex < pathParts.length) {
                    String userIdStr = pathParts[userIdIndex];
                    try {
                         userId = Integer.parseInt(userIdStr);
                        System.out.println("log is here to read user from readuserRR--->");
                        System.out.println(userId);
                        LogContext.setResource(Integer.toString(userId));
                        us = new ReadUserDAO(con,userId).access().getOutputParam();
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
                    } catch (NumberFormatException e) {
                        // Handle the case where the user ID is not a valid integer
                        System.err.println("Invalid user ID: " + userIdStr);
                    }
                } else {
                    // Handle the case where the "user" segment is not found in the path or the user ID is missing
                    System.err.println("User ID not found in the path");
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
