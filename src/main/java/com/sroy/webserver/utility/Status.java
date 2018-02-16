package com.sroy.webserver.utility;

/**
 * Created by shamalroy
 */
public enum Status {
    HTTP_200("200 OK"),
    HTTP_301("301 Moved Permanently"),
    HTTP_400("400 Bad Request"),
    HTTP_401("401 Unauthorized"),
    HTTP_404("404 Not Found"),
    HTTP_500("500 Internal Server Error"),
    HTTP_502("502 Bad Gateway");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
