package gorest.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class R {

        private static final Logger LOGGER = LogManager.getLogger(R.class);
        private static final String RESOURCES_PATH = "src/main/resources/";
        private static final String CONFIG_PATH = RESOURCES_PATH + "config.properties";

        private R() {
        }

        private static Object getFromProperty(String path, String key) {
            Properties properties = loadProperties(path);
            return properties != null ? properties.get(key) : null;
        }

        private static Properties loadProperties(String filePath) {
            Properties properties = new Properties();
            try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
                properties.load(fileInputStream);
                return properties;
            } catch (IOException e) {
                LOGGER.error("The problem occurred while loading properties");
                return null;
            }
        }

        public static class CONFIG {

            private CONFIG() {
            }

            public static Object get(String key) {
                return getFromProperty(CONFIG_PATH, key);
            }

            public static String getString(String key) {
                return (String) get(key);
            }
        }
}
