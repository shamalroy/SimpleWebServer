package com.sroy.webserver.http.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shamalroy
 */
public class RequestContext {
    private String httpVersion;
    private String method;
    private String path;
    private Map<String, String> requestHeaders;

    public RequestContext() {
        requestHeaders = new HashMap<>();
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void addRequestHeader(String key, String value) {
        this.requestHeaders.put(key, value);
    }
}
