package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.jdbc.JdbcException;

public class ServiceException extends JdbcException{//// FIXME: 27.11.2016 dont know about that
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
