package com.sroy.webserver;

import com.sroy.webserver.httpserver.HttpServer;

/**
 * Created by shamalroy
 */
public class Main {

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
