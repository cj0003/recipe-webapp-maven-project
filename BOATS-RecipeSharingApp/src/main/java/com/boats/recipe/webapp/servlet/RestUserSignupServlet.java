package com.boats.recipe.webapp.servlet;

import com.boats.recipe.webapp.database.CreateUserDAO;
import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.Message;
import com.boats.recipe.webapp.rest.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "SignupServlet", value = "/signup")
public class RestUserSignupServlet extends AbstractDatabaseServlet{
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    private static final long serialVersionUID = 1L;
    private String message;

    public void init() {
        message = "Hello World! now you are in Signup servlet";
    }

//    public CreateUserDAO createUserDAO = new CreateUserDAO(getConnection(),);

    public RestUserSignupServlet(){
        super();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws  IOException {
    response.getWriter().append("Served at: ").append(request.getContextPath());
//    RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
//        dispatcher.forward(request, response);
        }




    private boolean processUserRegister(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;
        if (path.lastIndexOf("rest/user") <= 0) {
            System.out.println("Inside if--->");
            return false;
        }
        // strip everything until after the /user
        path = path.substring(req.getContextPath().length() + "/rest/user".length());

        // the request URI is: /user
        // if method GET, search for users in db
        // if method POST, create user
        if (path.isEmpty() || path.equals("/")) {

            System.out.println("Path is not empty");
            System.out.println(method);
            switch (method) {

                case "POST":
                    LOGGER.warn("HTTP POST Method requested!! Post method works");
                    new CreateUserRR(req, res, getConnection()).serve();
                    break;
                default:
                    LOGGER.warn("Unsupported operation for URI /user: %s.", method);

                    m = new Message("Unsupported operation for URI /user.", "E4A5",
                            String.format("Requested operation %s.", method));
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
                    break;
            }

        }else{
            switch (method) {
                case "GET":
                    new ReadUserRR(req, res, getConnection()).serve();
                    break;
                case "PUT":
                    new UpdateUserRR(req, res, getConnection()).serve();
                    break;

                default:
                    LOGGER.warn("Unsupported operation for URI /user/{badge}: %s.", method);

                    m = new Message("Unsupported operation for URI /user/{badge}.", "E4A5",
                            String.format("Requested operation %s.", method));
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
            }
        }
        return true;
    }



}
