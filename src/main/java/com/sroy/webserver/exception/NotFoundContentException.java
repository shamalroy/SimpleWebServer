package com.sroy.webserver.exception;

/**
 * Created by shamalroy
 */
public class NotFoundContentException extends Exception{
    public NotFoundContentException(String message) {
        super(message);
    }

    public NotFoundContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
