package com.boats.recipe.webapp.servlet;

import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.Message;
import com.boats.recipe.webapp.rest.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

import static java.lang.System.out;

public final class RestDispatcherServlet extends AbstractDatabaseServlet{
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        LogContext.setIPAddress(req.getRemoteAddr());

        final OutputStream out = res.getOutputStream();
        try{
            if(processRecipe(req, res)){
                return;
            }
            LOGGER.warn("Unknown resource requested: %s.", req.getRequestURI());

            final Message m = new Message("Unknown resource requested.", "E4A6",
                    String.format("Requested resource is %s.", req.getRequestURI()));
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.setContentType(JSON_UTF_8_MEDIA_TYPE);
            m.toJSON(out);
        }catch (Throwable t){
            LOGGER.error("Unexpected error while processing the REST resource.", t);

            final Message m = new Message("Unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(out);
        }finally {
            if (out != null) {
                out.flush();
                out.close();
            }

            LogContext.removeIPAddress();
        }
    }

    private boolean processRecipe(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;
        if (path.lastIndexOf("rest/recipe") <= 0) {
            System.out.println("Inside if--->");
            return false;
        }

        // strip everything until after the /recipe
        path = path.substring(req.getContextPath().length() + "/rest/recipe".length());

        // the request URI is: /recipe
        // if method GET, list recipe
        // if method POST, create recipe
        if (path.isEmpty() || path.equals("/")) {

            System.out.println("PAth is not empty");
            System.out.println(method);
            switch (method) {
                case "GET":
                    new ListRecipeRR(req, res, getConnection()).serve();
                    break;
                case "POST":
                    LOGGER.warn("HTTP POST Method requested!! IT's working!! YEaahhh");
                    new CreateRecipeRR(req, res, getConnection()).serve();
                    break;
                default:
                    LOGGER.warn("Unsupported operation for URI /recipe: %s.", method);

                    m = new Message("Unsupported operation for URI /recipe.", "E4A5",
                            String.format("Requested operation %s.", method));
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
                    break;
            }

            }else{
            switch (method) {
                case "GET":
                    new ReadRecipeRR(req, res, getConnection()).serve();
                    break;
                case "PUT":
                    new UpdateRecipeRR(req, res, getConnection()).serve();
                    break;
                case "DELETE":
                    new DeleteRecipeRR(req, res, getConnection()).serve();
                    break;
                default:
                    LOGGER.warn("Unsupported operation for URI /recipe/{badge}: %s.", method);

                    m = new Message("Unsupported operation for URI /recipe/{badge}.", "E4A5",
                            String.format("Requested operation %s.", method));
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
              }
            }
        return true;
    }
}
