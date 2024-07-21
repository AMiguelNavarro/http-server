package com.alberto.httpserver;

import com.alberto.httpserver.config.Configuration;
import com.alberto.httpserver.config.ConfigurationManager;
import com.alberto.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Driver class for the http server
 */
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    private static final String JSON_PATH = "src/main/resources/http.json";

    public static void main(String[] args) {
        LOGGER.info("Server starting...");
        loadConfigFile();
        Configuration config = ConfigurationManager.getInstance().getCurrentConfig();

        LOGGER.info("Using port: {}", config.getPort());
        LOGGER.info("Using web root: {}", config.getWebRoot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(config.getPort(), config.getWebRoot());
            serverListenerThread.start();
        } catch (IOException e) {
            LOGGER.info("Error with server listener thread{}", e.getLocalizedMessage());
        }

    }

    private static void loadConfigFile() {
        ConfigurationManager.getInstance().loadConfigFile(JSON_PATH);
    }
}
