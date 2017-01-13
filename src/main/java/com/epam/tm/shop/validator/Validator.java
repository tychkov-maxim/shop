package com.epam.tm.shop.validator;

public interface Validator {

    boolean isValid(String value);

    String getMessage();

    void setMessage(String message);

}
