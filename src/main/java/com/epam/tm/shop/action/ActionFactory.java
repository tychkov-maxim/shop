package com.epam.tm.shop.action;


import com.epam.tm.shop.util.PropertyManager;
import com.epam.tm.shop.util.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ActionFactory {

    public static final Logger log = LoggerFactory.getLogger(ActionFactory.class);

    private static final String ACTION_PROPERTIES_FILE_NAME = "action.properties";

    private Map<String, String> actions;

    public ActionFactory() throws ActionFactoryException {

        try {
            PropertyManager propertyManager = new PropertyManager(ACTION_PROPERTIES_FILE_NAME);
            actions = propertyManager.getHashMap();
            log.debug("{} actions was read", actions.size());
        } catch (PropertyManagerException e) {
            throw new ActionFactoryException(e);
        }
    }

    public Action getAction(String actionName) throws ActionFactoryException {

        String actionClassPath = null;

        for (Map.Entry<String, String> entry : actions.entrySet()) {
            if (entry.getKey().equals(actionName))
                actionClassPath = entry.getValue();
        }
        if (actionClassPath == null) throw new ActionFactoryException("couldn't find action");
        try {
            Class<?> actionClass = Class.forName(actionClassPath);
            log.trace("{} action was created", actionName);
            return (Action) actionClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ActionFactoryException(e);
        }
    }
}
