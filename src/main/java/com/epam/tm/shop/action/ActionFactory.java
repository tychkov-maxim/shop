package com.epam.tm.shop.action;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private Map<String,Class> matches;

    public ActionFactory() {
        matches = new HashMap<>();
        matches.put("login",LoginAction.class);
        matches.put("logout",LogoutAction.class);
        matches.put("register",UserRegisterAction.class);
        matches.put("show",ProductsByCategoryAction.class);
        matches.put("lang",ChangeLanguageAction.class);
        matches.put("product",ProductAction.class);
    }

    public Action getAction(String actionName) throws ActionFactoryException {
        Class aClass = matches.get(actionName);
        try {
            return (Action)aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ActionFactoryException(e);
        }
    }
}
