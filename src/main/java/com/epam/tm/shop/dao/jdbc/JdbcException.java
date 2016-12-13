package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.DaoException;

public class JdbcException extends DaoException {
    public JdbcException(String message) {
        super(message);
    }

    public JdbcException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcException(Throwable cause) {
        super(cause);
    }
}
