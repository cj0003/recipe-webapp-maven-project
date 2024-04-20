package com.boats.recipe.webapp.resources;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.*;

/**
 * Represents a message or an error message.
 * This class provides methods to create and handle messages and errors.
 */

public class Message extends AbstractResource {

    /**
     * The message content.
     */
    private final String message;

    /**
     * The error code associated with the message, if any.
     */
    private final String errorCode;

    /**
     * Additional details about the error, if any.
     */
    private final String errorDetails;

    /**
     * Indicates whether the message is an error message or not.
     */
    private final boolean isError;


    /**
     * Creates an error message with specified content, error code, and details.
     *
     * @param message
     *            the message content.
     * @param errorCode
     *            the error code associated with the message.
     * @param errorDetails
     *            additional details about the error.
     */
    public Message(final String message, final String errorCode, final String errorDetails) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.isError = true;
    }


    /**
     * Creates a generic message with specified content.
     *
     * @param message
     *            the message content.
     */
    public Message(final String message) {
        this.message = message;
        this.errorCode = null;
        this.errorDetails = null;
        this.isError = false;
    }


    /**
     * Returns the message content.
     *
     * @return the message content.
     */
    public final String getMessage() {
        return message;
    }

    /**
     * Returns the error code associated with the message, if any.
     *
     * @return the error code associated with the message, or {@code null} if no error code is set.
     */
    public final String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns additional details about the error, if any.
     *
     * @return additional details about the error, or {@code null} if no error details are set.
     */
    public final String getErrorDetails() {
        return errorDetails;
    }

    /**
     * Indicates whether the message is an error message.
     *
     * @return {@code true} if the message is an error message, {@code false} otherwise.
     */
    public final boolean isError() {
        return isError;
    }

    @Override
    protected void writeJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("message");

        jg.writeStartObject();

        jg.writeStringField("message", message);

        if(errorCode != null) {
            jg.writeStringField("error-code", errorCode);
        }

        if(errorDetails != null) {
            jg.writeStringField("error-details", errorDetails);
        }

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

}
