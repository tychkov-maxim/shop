package com.epam.tm.shop.util;

public class PropertyManagerException extends Exception {

    public PropertyManagerException(String message) {
        super(message);
    }

    public PropertyManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyManagerException(Throwable cause) {
        super(cause);
    }
}
