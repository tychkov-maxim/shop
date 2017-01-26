package com.epam.tm.shop.exception;

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
