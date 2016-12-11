package com.epam.tm.shop.pool;

import java.sql.SQLException;

public class PooledConnectionException extends SQLException {

    public PooledConnectionException(String message) {
        super(message);
    }

    public PooledConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PooledConnectionException(Throwable cause) {
        super(cause);
    }
}
