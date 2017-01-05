package com.epam.tm.shop.service;

public class ServiceNoDataException  extends Exception{
    public ServiceNoDataException(String message) {
        super(message);
    }

    public ServiceNoDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceNoDataException(Throwable cause) {
        super(cause);
    }
}
