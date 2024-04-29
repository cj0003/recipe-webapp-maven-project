package com.boats.recipe.webapp.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.http.HttpServlet;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractDatabaseServlet extends HttpServlet {
    protected static final Logger LOGGER = LogManager.getLogger(AbstractDatabaseServlet.class,
            StringFormatterMessageFactory.INSTANCE);

    private DataSource ds;

    public void init(ServletConfig config) throws ServletException{
        InitialContext cxt;

        try {
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/boats");

            LOGGER.info("Connection pool to the database successfully acquired.");
        } catch (NamingException e) {
            ds = null;

            LOGGER.error("Unable to acquire the connection pool to the database.", e);

            throw new ServletException("Unable to acquire the connection pool to the database", e);
        }

    }
    public void destroy() {
        ds = null;
        LOGGER.info("Connection pool to the database successfully released.");
    }

    /**
     * Returns a {@link  Connection} for accessing the database.
     *
     * @return a {@link Connection} for accessing the database
     *
     * @throws SQLException if anything goes wrong in obtaining the connection.
     */
    protected final Connection getConnection() throws SQLException {
        try {
            return ds.getConnection();
        } catch (final SQLException e) {
            LOGGER.error("Unable to acquire the connection from the pool.", e);
            throw e;
        }
    }
}
