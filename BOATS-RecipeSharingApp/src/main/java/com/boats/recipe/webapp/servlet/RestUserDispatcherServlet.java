package com.boats.recipe.webapp.servlet;

import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.Message;
import com.boats.recipe.webapp.rest.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

public class RestUserDispatcherServlet extends AbstractDatabaseServlet {
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";
    public void init()
    {
        LOGGER.info("Initializing RestUserDispatcherServlet");
    };
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        LogContext.setIPAddress(req.getRemoteAddr());

        final OutputStream out = res.getOutputStream();
        try{
                if(processUser(req,res))
                {
                    return;
                }


            LOGGER.warn("Unknown resource requested: %s.", req.getRequestURI());

            final Message m = new Message("Unknown resource requested.", "E4A6",
                    String.format("Requested resource is %s.", req.getRequestURI()));
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.setContentType(JSON_UTF_8_MEDIA_TYPE);
            m.toJSON(out);
        }

        catch(Exception e)
        {
            System.out.println(e);
        }


    }


private boolean processUser(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
    {
        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;


        // strip everything until after the /user
        path = path.substring(req.getContextPath().length() + "/rest/user".length());

        if (path.isEmpty() || path.equals("/")) {

            System.out.println("Path is not empty");
            System.out.println(method);

            // if method GET, list users
            // if method POST, create user
            switch (method)
            {
                case "POST":
                    LOGGER.warn("HTTP POST Method requested!");
                    new CreateUserRR(req, res, getConnection()).doServe();
                    break;
                case "GET":
                    LOGGER.warn("HTTP GET Method requested!");
                    new ListUserRR(req, res, getConnection()).doServe();
                    break;
                default:
                    LOGGER.warn("Unsupported operation for URI /user");

                    m = new Message("Unsupported operation for URI /user.", "E4A5",
                            String.format("Requested operation %s.", method));
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
                    break;

            }
        }else
        {
            LOGGER.warn("Unsupported operation for URI /user in else");

            m = new Message("Unsupported operation for URI /user.", "E4A5",
                    String.format("Requested operation %s.", method));
            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            m.toJSON(res.getOutputStream());

        }

        }
        return true;
    }
}
