package com.alberto.httpserver;

import com.alberto.httpserver.config.Configuration;
import com.alberto.httpserver.config.ConfigurationManager;

/**
 * Driver class for the http server
 */
public class HttpServer {

    private static final String JSON_PATH = "src/main/resources/http.json";

    public static void main(String[] args) {
        System.out.println("Server starting...");

        ConfigurationManager configManager = ConfigurationManager.getInstance();
        configManager.loadConfigFile(JSON_PATH);
        Configuration currentConfig = configManager.getCurrentConfig();

        System.out.println("Using port: " + currentConfig.getPort());
        System.out.println("Using web root: " + currentConfig.getWebRoot());

        System.out.println("Server finished");
    }
}
