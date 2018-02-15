package com.sroy.webserver.filter.post;

import com.sroy.webserver.filter.Filter;
import com.sroy.webserver.http.HttpRequest;
import com.sroy.webserver.http.context.ResponseContext;
import com.sroy.webserver.utility.Constants;

import java.util.Date;

/**
 * Created by shamalroy
 */
public class ServerHeaderPostFilter extends Filter {


    @Override
    public void doFilter(HttpRequest httpRequest) {
        ResponseContext responseContext = httpRequest.getHttpResponse().getResponseContext();
        responseContext.addResponseHeader(Constants.HTTP_HEADER_SERVER, Constants.SERVER_NAME);
        responseContext.addResponseHeader(Constants.HTTP_HEADER_DATE, String.valueOf(new Date()));

    }
}
