package com.sroy.webserver.http;

import com.sroy.webserver.config.ServerConfiguration;
import com.sroy.webserver.content.impl.FileContent;
import com.sroy.webserver.utility.Utils;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shamalroy
 */
public class HttpHandler implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(HttpHandler.class.getName());
    private Socket clientSocket;
    private ServerConfiguration config;


    private String threadId;

    public HttpHandler(Socket socket, ServerConfiguration config) {
        this.clientSocket = socket;
        this.config = config;
    }

    @Override
    public void run() {

        if (Utils.isEmptyOrNull(threadId)) {
            threadId = String.valueOf(UUID.randomUUID());
        }
        LOGGER.log(Level.INFO, "Thread # " + threadId);

        handle(clientSocket);
        closeSocket(clientSocket);
    }


    public void handle(Socket socket) {

        try {
            FileContent fileContent = new FileContent(config.getDocRoots());
            HttpResponse httpResponse = new HttpResponse(fileContent);

            HttpRequest httpRequest = new HttpRequest(socket, httpResponse);
            httpRequest.process();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing client socket.", e);
        }
    }

    private void closeSocket(Socket clientSocket) {
        try {
            if (!clientSocket.getKeepAlive()) {
                clientSocket.close();
                LOGGER.log(Level.INFO, "Socket closed.");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error closing client socket.", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error.", e);
        }
    }
}
