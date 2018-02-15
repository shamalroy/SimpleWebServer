package com.sroy.webserver.http.context;

import com.sroy.webserver.utility.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shamalroy
 */
public class ResponseContext {

    private Map<String, String> responseHeaders;
    byte[] responseContentBody;
    Status status;

    public ResponseContext() {
        responseHeaders = new HashMap<>();
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public void addResponseHeader(String key, String value) {
        this.responseHeaders.put(key, value);
    }

    public byte[] getResponseContentBody() {
        return responseContentBody;
    }

    public void setResponseContentBody(byte[] responseContentBody) {
        this.responseContentBody = responseContentBody;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
