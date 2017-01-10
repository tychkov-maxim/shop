package com.epam.tm.shop.service;

public class ServiceExceptionError extends Exception {
    public ServiceExceptionError(String message) {
        super(message);
    }

    public ServiceExceptionError(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceExceptionError(Throwable cause) {
        super(cause);
    }
}
