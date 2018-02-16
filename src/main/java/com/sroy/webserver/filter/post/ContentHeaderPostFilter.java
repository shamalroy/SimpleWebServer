package com.sroy.webserver.filter.post;

import com.sroy.webserver.filter.Filter;
import com.sroy.webserver.http.HttpRequest;
import com.sroy.webserver.http.context.ResponseContext;
import com.sroy.webserver.utility.Constants;
import com.sroy.webserver.utility.MimeType;
import com.sroy.webserver.utility.Status;

/**
 * Created by shamalroy
 */
public class ContentHeaderPostFilter extends Filter {

    @Override
    public void doFilter(HttpRequest httpRequest) {
        ResponseContext responseContext = httpRequest.getHttpResponse().getResponseContext();

        if (responseContext.getStatus() == Status.HTTP_200) {
            String path = httpRequest.getRequestContext().getPath();
            MimeType mimeType = detectMimeType(path);
            if (mimeType != null) {
                responseContext.addResponseHeader(Constants.HTTP_HEADER_CONTENT_TYPE, mimeType.toString());
            }
            responseContext.addResponseHeader(Constants.HTTP_HEADER_CONTENT_LENGTH, String.valueOf(responseContext.getResponseContentBody().length));
        }
    }

    private MimeType detectMimeType(String path) {
        String fileExtension = path.substring(path.lastIndexOf(".") + 1, path.length());
        MimeType mimeType = MimeType.get(fileExtension);
        return mimeType;
    }
}
