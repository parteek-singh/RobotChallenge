package org.game.robo.configs;

import org.game.robo.exception.RobotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
    private static final Properties configuration = new Properties();

    private PropertiesLoader(){}

    private static class PropertiesLoaderSingleton {
        private static final PropertiesLoader INSTANCE = new PropertiesLoader();
    }

    public static PropertiesLoader getInstance() {
        return PropertiesLoaderSingleton.INSTANCE;
    }

    public void loadProperties() {

        try (InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            configuration.load(inputStream);
        } catch (Exception e) {
            logger.error("Not able to read application configuration ", e);
            throw new RobotException("Not able to read application configuration");
        }
    }

    public Properties getConfiguration() {
        return configuration;
    }
    public String getProperty(String key) {
        return configuration.getProperty(key);
    }

    public void addProperty(String key, String value) {
        configuration.setProperty(key, value);
    }
}
