package com.sroy.webserver.httpserver;

import com.sroy.webserver.config.ServerConfiguration;
import com.sroy.webserver.http.HttpHandler;
import com.sroy.webserver.utility.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shamalroy
 */
public class HttpServer {
    private static final Logger LOGGER = Logger.getLogger(HttpServer.class.getName());
    private ServerSocket serverSocket;
    private boolean isServerRunning;
    private ServerConfiguration config;

    public void start() {
        isServerRunning = true;
        config = new ServerConfiguration();
        try {
            InetAddress bindAddress = InetAddress.getByName(config.getBindAddress());
            serverSocket = new ServerSocket(config.getPort(), config.getBacklog(), bindAddress);

            LOGGER.log(Level.INFO, "Welcome! Web Server started.");
            LOGGER.log(Level.INFO, "Server address: " + Constants.HTTP_PROTOCOL + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort() + "/index.html");

            while (isServerRunning) {
                Socket clientSocket = serverSocket.accept();
                ExecutorService executor = Executors.newFixedThreadPool(config.getNoOfThreadPool());
                executor.submit(new HttpHandler(clientSocket, config));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error starting the web server.", e);
        }
    }

    public void stop() {
        isServerRunning = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error closing the server socket.", e);
        }
    }
}
