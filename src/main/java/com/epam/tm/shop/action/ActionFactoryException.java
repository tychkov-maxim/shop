package com.epam.tm.shop.action;

public class ActionFactoryException extends Exception {

    public ActionFactoryException(String message) {
        super(message);
    }

    public ActionFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionFactoryException(Throwable cause) {
        super(cause);
    }
}
