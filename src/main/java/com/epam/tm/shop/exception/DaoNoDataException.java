package com.epam.tm.shop.exception;

public class DaoNoDataException extends Exception {
    public DaoNoDataException(String message) {
        super(message);
    }

    public DaoNoDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoNoDataException(Throwable cause) {
        super(cause);
    }
}
