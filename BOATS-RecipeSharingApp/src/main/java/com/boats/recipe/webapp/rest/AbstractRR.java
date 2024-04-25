package com.boats.recipe.webapp.rest;

import com.boats.recipe.webapp.resources.LogContext;
import com.boats.recipe.webapp.resources.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;

public abstract class AbstractRR implements RestResource{

    protected static final Logger LOGGER = LogManager.getLogger(AbstractRR.class,
            StringFormatterMessageFactory.INSTANCE);

    protected static final String JSON_MEDIA_TYPE = "application/json";

    protected static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    protected static final String ALL_MEDIA_TYPE = "*/*";

    protected final HttpServletRequest req;

    protected final HttpServletResponse res;

    protected final Connection con;

    private final String action;

    protected AbstractRR(final String action, final HttpServletRequest req, final HttpServletResponse res, final Connection con){
        if (action == null || action.isBlank()) {
            LOGGER.warn("Action is null or empty.");
        }
        this.action = action;
        LogContext.setAction(action);

        if (req == null) {
            LOGGER.error("The HTTP request cannot be null.");
            throw new NullPointerException("The HTTP request cannot be null.");
        }
        this.req = req;

        if (res == null) {
            LOGGER.error("The HTTP response cannot be null.");
            throw new NullPointerException("The HTTP response cannot be null.");
        }
        this.res = res;

        if (con == null) {
            LOGGER.error("The connection cannot be null.");
            throw new NullPointerException("The connection cannot be null.");
        }
        this.con = con;
    }

    @Override
    public void serve() throws IOException {
        try {
            // if the request method and/or the MIME media type are not allowed, return.
            // Appropriate error message sent by {@code checkMethodMediaType}
            if (!checkMethodMediaType(req, res)) {
                return;
            }

            doServe();
        } catch (Throwable t) {
            LOGGER.error("Unable to serve the REST request.", t);

            final Message m = new Message(String.format("Unable to serve the REST request: %s.", action), "E5A1",
                    t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        } finally {
            LogContext.removeAction();
            LogContext.removeResource();
        }
    }

    protected abstract void doServe() throws IOException;

    protected boolean checkMethodMediaType(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        final String method = req.getMethod();
        final String contentType = req.getHeader("Content-Type");
        final String accept = req.getHeader("Accept");
        final OutputStream out = res.getOutputStream();

        Message m = null;

        if (accept == null) {
            LOGGER.error("Output media type not specified. Accept request header missing.");
            m = new Message("Output media type not specified.", "E4A1", "Accept request header missing.");
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(out);
            return false;
        }

        if (!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
            LOGGER.error("Unsupported output media type. Resources are represented only in application/json. Requested representation is %s.", accept);
            m = new Message("Unsupported output media type. Resources are represented only in application/json.",
                    "E4A2", String.format("Requested representation is %s.", accept));
            res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            m.toJSON(out);
            return false;
        }

        // if the method is supposed to send a body, check its MIME media type
        switch (method) {
            case "GET":
            case "DELETE":
                // nothing to do
                break;

            case "POST":
            case "PUT":
                if (contentType == null) {
                    LOGGER.error("Input media type not specified. Content-Type request header missing.");
                    m = new Message("Input media type not specified.", "E4A3", "Content-Type request header missing.");
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(out);
                    return false;
                }

                if (!contentType.contains(JSON_MEDIA_TYPE)) {
                    LOGGER.error(
                            "Unsupported input media type. Resources are represented only in application/json. Submitted representation is %s.",
                            contentType);
                    m = new Message("Unsupported input media type. Resources are represented only in application/json.",
                            "E4A4", String.format("Submitted representation is %s.", contentType));
                    res.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                    m.toJSON(out);
                    return false;
                }

                break;
            default:
                LOGGER.error("Unsupported operation. Requested operation %s.", method);
                m = new Message("Unsupported operation.", "E4A5", String.format("Requested operation %s.", method));
                res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                m.toJSON(out);
                return false;
        }

        return true;
    }
}
