package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.DaoNonUniqueFieldException;

public class JdbcNonUniqueFieldException extends DaoNonUniqueFieldException {
    public JdbcNonUniqueFieldException(String message) {
        super(message);
    }

    public JdbcNonUniqueFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcNonUniqueFieldException(Throwable cause) {
        super(cause);
    }
}
