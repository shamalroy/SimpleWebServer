package com.sroy.webserver.filter;

import com.sroy.webserver.http.HttpRequest;

/**
 * Created by shamalroy
 */
public abstract class Filter {
    private Filter nextFilter;

    public abstract void doFilter(HttpRequest httpRequest);

    public Filter chain(Filter nextFilter) {
        this.nextFilter = nextFilter;
        return this;
    }

    public void start(HttpRequest httpRequest) {
        doFilter(httpRequest);
        if (nextFilter != null) {
            nextFilter.start(httpRequest);
        }
    }
}
