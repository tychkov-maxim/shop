package com.epam.tm.shop.exception;

public class ServiceNonUniqueFieldException extends Exception {
    public ServiceNonUniqueFieldException(String message) {
        super(message);
    }

    public ServiceNonUniqueFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceNonUniqueFieldException(Throwable cause) {
        super(cause);
    }
}
