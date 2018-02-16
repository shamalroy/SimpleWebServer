package com.sroy.webserver.filter.post;

import com.sroy.webserver.filter.Filter;
import com.sroy.webserver.http.HttpRequest;
import com.sroy.webserver.utility.Constants;
import com.sroy.webserver.utility.Status;

import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shamalroy
 */
public class KeepAlivePostFilter extends Filter {
    private static final Logger LOGGER = Logger.getLogger(KeepAlivePostFilter.class.getName());

    @Override
    public void doFilter(HttpRequest httpRequest) {

        if (httpRequest.getHttpResponse().getResponseContext().getStatus() == Status.HTTP_200) {
            if (httpRequest.getRequestContext().getRequestHeaders().containsKey(Constants.HTTP_HEADER_CONNECTION)
                    && httpRequest.getRequestContext().getRequestHeaders().get(Constants.HTTP_HEADER_CONNECTION).equalsIgnoreCase(Constants.KEEP_ALIVE)) {
                httpRequest.getHttpResponse().getResponseContext().addResponseHeader(Constants.HTTP_HEADER_CONNECTION, Constants.KEEP_ALIVE);
            }
        } else {
            try {
                httpRequest.getSocket().setKeepAlive(false);
            } catch (SocketException e) {
                LOGGER.log(Level.SEVERE, "Error updating Keep-Alive status.", e);
            }
        }
    }
}
