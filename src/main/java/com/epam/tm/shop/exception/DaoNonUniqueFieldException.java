package com.epam.tm.shop.exception;

public class DaoNonUniqueFieldException extends Exception {
    public DaoNonUniqueFieldException(String message) {
        super(message);
    }

    public DaoNonUniqueFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoNonUniqueFieldException(Throwable cause) {
        super(cause);
    }
}
