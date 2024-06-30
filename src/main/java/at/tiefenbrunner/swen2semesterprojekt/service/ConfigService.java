package at.tiefenbrunner.swen2semesterprojekt.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigService {
    private final Properties properties;

    public ConfigService(String filePath) throws IOException {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        }
    }

    // Constructor with direct InputStream for tests
    public ConfigService(InputStream inputStream) throws IOException {
        properties = new Properties();
        properties.load(inputStream);
    }

    public void validateConfig() {
        // Check for DB config
        checkForConfig("db.driver");
        checkForConfig("db.url");
        checkForConfig("db.username");
        checkForConfig("db.password");

        if (properties.getProperty("db.username").equals("<DB-USERNAME HERE>"))
            throw new IllegalArgumentException("Missing configuration value for key: " + "db.username");

        if (properties.getProperty("db.password").equals("<DB-PASSWORD HERE>"))
            throw new IllegalArgumentException("Missing configuration value for key: " + "db.password");
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
}