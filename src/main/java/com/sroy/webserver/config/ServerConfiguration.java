package com.sroy.webserver.config;

import com.sroy.webserver.utility.Constants;
import com.sroy.webserver.utility.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by shamalroy
 */
public class ServerConfiguration {
    private static final Logger LOGGER = Logger.getLogger(ServerConfiguration.class.getName());

    public ServerConfiguration() {
        loadProperties();
    }
    private String bindAddress = "";
    private int port = 3333;
    private int backlog = 0;
    private int noOfThreadPool = 5;
    private String[] docRoots = new String[]{"."};

    public String getBindAddress() {
        return bindAddress;
    }

    public int getPort() {
        return port;
    }

    public int getBacklog() {
        return backlog;
    }

    public int getNoOfThreadPool() {
        return noOfThreadPool;
    }

    public String[] getDocRoots() {
        return docRoots;
    }

    private void loadProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(Constants.SERVER_CONFIG_PROPERTIES_FILE);
            prop.load(input);

            Set<String> propertyNames = prop.stringPropertyNames();

            for (String propertyName : propertyNames) {
                String propertyValue = prop.getProperty(propertyName);

                if (!Utils.isEmptyOrNull(propertyValue)) {
                    try {
                        Field field = this.getClass().getDeclaredField(propertyName);
                        if (field.getType().isAssignableFrom(Integer.TYPE)) {
                            field.set(this, Integer.valueOf(propertyValue));
                        } else if (field.getType().isAssignableFrom(Double.TYPE)) {
                            field.set(this, Double.valueOf(propertyValue));
                        } else if (field.getType() == String.class) {
                            field.set(this, propertyValue);
                        } else if (field.getType() == String[].class) {
                            field.set(this, propertyValue.split(","));
                        }
                    } catch (NoSuchFieldException e) {
                        LOGGER.log(Level.WARNING, "Invalid property name: " + propertyName + ".", e);
                    }
                }

            }

        } catch (IOException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Not able to load server-config.properties file.", e);
        }
    }
}
