package com.epam.tm.shop.exception;

public class HashGeneratorException extends Exception {
    public HashGeneratorException(String message) {
        super(message);
    }

    public HashGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public HashGeneratorException(Throwable cause) {
        super(cause);
    }
}
