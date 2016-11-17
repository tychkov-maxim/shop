package com.epam.tm.shop.pool;

import java.sql.SQLException;

public class PoolException extends SQLException{

    public PoolException(String message) {
        super(message);
    }

    public PoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolException(Throwable cause) {
        super(cause);
    }
}
