package com.alberto.httpserver.config;

import com.alberto.httpserver.config.exception.HttpConfigException;
import com.alberto.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 * SINGLETON CLASS to manage configuration files
 * It will be singleton because we just need one config manager shared across everything in the project
 */
public class ConfigurationManager {
    private ConfigurationManager() {}// Hidden constructor

    private static Configuration currentConfig;
    private static ConfigurationManager myConfigurationManager;

    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    /**
     * Method to load a configuration file located in the provided path
     * @param filePath of the configuration file
     */
    public void loadConfigFile(String filePath) {
        try (FileReader fileReader = new FileReader(filePath)) {
            StringBuffer sb = new StringBuffer();
            int i = 0;
            while ( (i = fileReader.read()) != -1 ) {
                sb.append((char) i);
            }
            JsonNode config = Json.parse(sb.toString());
            currentConfig = Json.fromJson(config, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigException("Error parsing json", e);
        } catch (IOException e) {
            throw new HttpConfigException("Error trying to read json from path: " + filePath, e);
        }
    }

    /**
     * Returns the current loaded configuration file
     */
    public Configuration getCurrentConfig() {
        if (currentConfig == null) {
            throw new HttpConfigException("No current configuration set");
        }
        return currentConfig;
    }


}
