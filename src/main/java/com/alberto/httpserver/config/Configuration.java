package com.alberto.httpserver.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object where we are going to map the http.json config file data
 */
public class Configuration {
    @JsonProperty(value = "port")
    private int port;
    @JsonProperty(value = "webroot")
    private String  webRoot;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebRoot() {
        return webRoot;
    }

    public void setWebRoot(String webRoot) {
        this.webRoot = webRoot;
    }
}
