package com.epam.tm.shop.validator;

public abstract class ValidatorImpl implements Validator {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
