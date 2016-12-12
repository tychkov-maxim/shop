package com.epam.tm.shop.dao;

public class DaoFactoryException extends Exception {
    public DaoFactoryException(String message) {
        super(message);
    }

    public DaoFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoFactoryException(Throwable cause) {
        super(cause);
    }
}
