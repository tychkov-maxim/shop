package com.epam.tm.shop.validator;

public abstract class Validator {
    private String message;

    abstract boolean isValid(String value);


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
