package com.epam.tm.shop.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    public static final Logger log = LoggerFactory.getLogger(PropertyManager.class);

    private Properties properties;

    public PropertyManager(String fileName) throws PropertyManagerException {
        properties = new Properties();
        try (InputStream is = PropertyManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) throw new PropertyManagerException("Error, unable to load: " + fileName);
            properties.load(is);
            log.trace("Property file: " + fileName + " was loaded successfully.");
        } catch (IOException e) {
            throw new PropertyManagerException("Error, unable to load: " + fileName,e);
        }
    }

    public String getPropertyKey(String key) throws PropertyManagerException {

        String value = properties.getProperty(key);

        if (value == null){
            throw new PropertyManagerException("Can not find value with this key: " + key);
        }

        return value;
    }

    public int getIntPropertyKey(String key) throws PropertyManagerException {
        return Integer.parseInt(getPropertyKey(key));
    }
}
