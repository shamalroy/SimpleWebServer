package com.sroy.webserver.http;

import com.sroy.webserver.filter.post.ContentHeaderPostFilter;
import com.sroy.webserver.filter.post.KeepAlivePostFilter;
import com.sroy.webserver.filter.post.ServerHeaderPostFilter;
import com.sroy.webserver.filter.pre.KeepAlivePreFilter;
import com.sroy.webserver.http.context.RequestContext;
import com.sroy.webserver.utility.Utils;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shamalroy
 */
public class HttpRequest {
    private static final Logger LOGGER = Logger.getLogger(HttpRequest.class.getName());
    private Socket socket;
    private RequestContext requestContext;
    //    private Filter filter;
    private HttpResponse httpResponse;

    public HttpRequest(Socket socket, HttpResponse httpResponse) {
        this.socket = socket;
//        this.filter = filter;
        this.httpResponse = httpResponse;
        requestContext = new RequestContext();
    }

    public void process() {
        try {
            byte[] data = new byte[16384];

            while ((socket.getInputStream().read(data, 0, data.length)) != -1) {

                processRequest(data);

                if (!socket.getKeepAlive()) {
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error", e);
        }
    }

    private void processRequest(byte[] data) {
        try {
            String inputData = new String(data);

            String[] inputDataLines = inputData.split(System.lineSeparator());
            for (String line : inputDataLines) {
                processRequestHeader(line);
            }

            runPreFilterChain();

            httpResponse.process(requestContext);

            runPostFilterChain();

            httpResponse.serve(socket.getOutputStream());

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error processing Http request.", e);
        }
    }

    private void processFirstRequestLine(String line) {
        LOGGER.log(Level.INFO, "Request <== " + " :: " + line);
        if (!Utils.isEmptyOrNull(line)) {

            String[] parts = line.trim().split(" ");
            requestContext.setMethod(parts[0]);
            requestContext.setPath(parts[1]);
            requestContext.setHttpVersion(parts[2]);
        }
    }

    private void processRequestHeader(String line) {
        if (line.startsWith("GET")) {
            processFirstRequestLine(line);
        } else {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                requestContext.addRequestHeader(parts[0].trim(), parts[1].trim());
            }
        }
    }

    private void runPreFilterChain() {
        new KeepAlivePreFilter()
                .start(this);
    }

    private void runPostFilterChain() {
        new ContentHeaderPostFilter()
                .chain(new ServerHeaderPostFilter())
                .chain(new KeepAlivePostFilter())
                .start(this);
    }

    public Socket getSocket() {
        return socket;
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }
}
