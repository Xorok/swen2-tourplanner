package at.tiefenbrunner.swen2semesterprojekt.service;

import jakarta.annotation.Nullable;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.Properties;

@Log4j2
public class ConfigService {
    private final Properties properties;
    private @Nullable String filePath;

    public ConfigService(String filePath) throws IOException {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        }
        this.filePath = filePath;
    }

    // Constructor with direct InputStream for tests
    public ConfigService(InputStream inputStream, OutputStream outputStream) throws IOException {
        properties = new Properties();
        properties.load(inputStream);
    }

    public void validateConfig() {
        // Check for DB config
        checkForConfig("app.theme");
        checkForConfig("db.driver");
        checkForConfig("db.url");
        checkForConfig("db.username");
        checkForConfig("db.password");
        checkForConfig("ors.api-key");

        if (!properties.getProperty("app.dark-theme").equals("true") &&
                !properties.getProperty("app.dark-theme").equals("false"))
            throw new IllegalArgumentException("Illegal configuration value for key: " + "app.dark-theme");

        if (properties.getProperty("db.username").equals("<DB-USERNAME HERE>"))
            throw new IllegalArgumentException("Missing configuration value for key: " + "db.username");

        if (properties.getProperty("db.password").equals("<DB-PASSWORD HERE>"))
            throw new IllegalArgumentException("Missing configuration value for key: " + "db.password");

        if (properties.getProperty("ors.api-key").equals("<OPENROUTESERVICE API-KEY HERE>"))
            throw new IllegalArgumentException("Missing configuration value for key: " + "ors.api-key");
    }

    private void checkForConfig(String key) {
        if (properties.getProperty(key) == null || properties.getProperty(key).isEmpty())
            throw new IllegalArgumentException("Missing configuration value for key: " + key);
    }

    public String getConfigValue(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Missing configuration value for key: " + key);
        }
        return value;
    }

    public void setConfigValue(String key, String value) {
        // Set new config in memory
        properties.setProperty(key, value);

        // Try to persist new config
        if (this.filePath != null) {
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                properties.store(outputStream, null);
                log.info("Saved new config value to disk!");
            } catch (IOException e) {
                log.error("Couldn't save new config value to disk!", e);
            }
        }
    }
}