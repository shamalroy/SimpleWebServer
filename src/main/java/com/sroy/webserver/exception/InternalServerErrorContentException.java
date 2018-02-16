package com.sroy.webserver.exception;

/**
 * Created by shamalroy
 */
public class InternalServerErrorContentException extends Exception{
    public InternalServerErrorContentException(String message) {
        super(message);
    }

    public InternalServerErrorContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
