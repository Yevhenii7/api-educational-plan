package gorest.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public final class R {

    private static final Logger LOGGER = LogManager.getLogger(R.class);

    private R() {}

    public static class CONFIG {
        private static final String CONFIG_FILE = "config.properties";
        private static final Properties PROPERTIES = loadProperties();

        private CONFIG() {}

        private static Properties loadProperties() {
            Properties props = new Properties();
            try (InputStream input = R.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                if (input == null) {
                    LOGGER.error("Could not find config file: " + CONFIG_FILE);
                    return props;
                }
                props.load(input);
            } catch (IOException e) {
                LOGGER.error("Error loading config properties", e);
            }
            return props;
        }

        public static String getString(String key) {
            return PROPERTIES.getProperty(key);
        }

        public static int getInt(String key, int defaultValue) {
            try {
                return Integer.parseInt(PROPERTIES.getProperty(key));
            } catch (NumberFormatException e) {
                LOGGER.warn("Invalid int for key: " + key);
                return defaultValue;
            }
        }

        public static boolean getBoolean(String key, boolean defaultValue) {
            String val = PROPERTIES.getProperty(key);
            return val != null ? Boolean.parseBoolean(val) : defaultValue;
        }
    }
}