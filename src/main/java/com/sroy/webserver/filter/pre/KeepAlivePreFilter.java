package com.sroy.webserver.filter.pre;

import com.sroy.webserver.filter.Filter;
import com.sroy.webserver.http.HttpRequest;
import com.sroy.webserver.utility.Constants;

import java.net.SocketException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shamalroy
 */
public class KeepAlivePreFilter extends Filter {
    private static final Logger LOGGER = Logger.getLogger(KeepAlivePreFilter.class.getName());

    @Override
    public void doFilter(HttpRequest httpRequest) {
        Map<String, String> requestHeaders = httpRequest.getRequestContext().getRequestHeaders();

        try {
            if (requestHeaders.containsKey(Constants.HTTP_HEADER_CONNECTION) && requestHeaders.get(Constants.HTTP_HEADER_CONNECTION).equalsIgnoreCase(Constants.KEEP_ALIVE)) {
                httpRequest.getSocket().setKeepAlive(true);
            } else {
                httpRequest.getSocket().setKeepAlive(false);
            }

        } catch (SocketException e) {
            LOGGER.log(Level.SEVERE, "Error updating Keep-Alive status.", e);
        }
    }
}
