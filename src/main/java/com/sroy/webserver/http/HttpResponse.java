package com.sroy.webserver.http;

import com.sroy.webserver.content.impl.FileContent;
import com.sroy.webserver.exception.InternalServerErrorContentException;
import com.sroy.webserver.exception.NotFoundContentException;
import com.sroy.webserver.http.context.RequestContext;
import com.sroy.webserver.http.context.ResponseContext;
import com.sroy.webserver.utility.Constants;
import com.sroy.webserver.utility.Status;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shamalroy
 */
public class HttpResponse {
    private static final Logger LOGGER = Logger.getLogger(HttpResponse.class.getName());
    private ResponseContext responseContext;
    private RequestContext requestContext;
    private FileContent fileContent;

    public HttpResponse(FileContent fileContent) {
        responseContext = new ResponseContext();
        this.fileContent = fileContent;
    }

    public void process(RequestContext requestContext) {
        this.requestContext = requestContext;
        fileContent.setPath(requestContext.getPath());

        Status status;
        try {
            responseContext.setResponseContentBody(fileContent.loadContent());
            status = Status.HTTP_200;
        } catch (InternalServerErrorContentException e) {
            status = Status.HTTP_500;
        } catch (NotFoundContentException e) {
            status = Status.HTTP_404;
        }

        responseContext.setStatus(status);
    }

    public void serve(OutputStream outputStream) {

        LOGGER.log(Level.INFO, "Response ==> " + responseContext.getStatus().toString() + " :: " + requestContext.getPath());
        PrintStream printStream = new PrintStream(outputStream);

        printStream.println(Constants.HTTP_VERSION + " " + responseContext.getStatus().toString());

        for (Map.Entry<String, String> responseHeaderEntry : responseContext.getResponseHeaders().entrySet()) {
            printStream.println(responseHeaderEntry.getKey() + ": " + responseHeaderEntry.getValue());
        }
        printStream.println();
        if (responseContext.getStatus() == Status.HTTP_200) {
            try {
                printStream.write(responseContext.getResponseContentBody());
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error serving response." + e);
            }
        } else {
            printStream.println(responseContext.getStatus().toString());
        }
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public ResponseContext getResponseContext() {
        return responseContext;
    }
}
