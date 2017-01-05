package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.DaoNoDataException;

public class JdbcNoDataException extends DaoNoDataException{

    public JdbcNoDataException(String message) {
        super(message);
    }

    public JdbcNoDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcNoDataException(Throwable cause) {
        super(cause);
    }
}
