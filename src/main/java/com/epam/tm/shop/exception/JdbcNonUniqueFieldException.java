package com.epam.tm.shop.exception;

import com.epam.tm.shop.exception.DaoNonUniqueFieldException;

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
