package com.epam.tm.shop.validator;

import java.util.List;

public class LengthValidator extends Validator {

    private int maxLength;
    private int minLength;

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(String value) {
        int length = value.length();
        return minLength < length && length > maxLength;
    }
}
