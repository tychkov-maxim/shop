package com.epam.tm.shop.util;

import com.epam.tm.shop.exception.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PropertyManager {
    private static final Logger log = LoggerFactory.getLogger(PropertyManager.class);

    private Properties properties;

    public PropertyManager(String fileName) throws PropertyManagerException {
        properties = new Properties();
        try (InputStream is = PropertyManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) throw new PropertyManagerException("Error, unable to load: " + fileName);
            properties.load(is);
            log.trace("Property file: " + fileName + " was loaded successfully.");
        } catch (IOException e) {
            throw new PropertyManagerException("Error, unable to load: " + fileName, e);
        }
    }

    public String getPropertyKey(String key) throws PropertyManagerException {

        String value = properties.getProperty(key);

        if (value == null) {
            throw new PropertyManagerException("Can not find value with this key: " + key);
        }


        return value;
    }

    public Enumeration<?> getPropertyNames() {
        return properties.propertyNames();
    }

    public int getIntPropertyKey(String key) throws PropertyManagerException {
        return Integer.parseInt(getPropertyKey(key));
    }

    public Map<String, String> getHashMap() {
        Map<String, String> map = new HashMap<>();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

    public List<String> getListWithPrefix(String prefix) {
        List<String> list = new ArrayList<>();

        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            if (key.startsWith(prefix))
                list.add(properties.getProperty(key));
        }
        return list;
    }
}
