package com.sroy.webserver.content;

import com.sroy.webserver.exception.InternalServerErrorContentException;
import com.sroy.webserver.exception.NotFoundContentException;

import java.io.IOException;

/**
 * Created by shamalroy
 */
public interface Content {
    byte[] loadContent() throws IOException, InternalServerErrorContentException, NotFoundContentException;
}
