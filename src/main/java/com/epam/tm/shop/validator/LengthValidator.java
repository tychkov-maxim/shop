package com.epam.tm.shop.validator;

public class LengthValidator extends ValidatorImpl {

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
        if (value == null) return false;
        int length = value.length();
        return minLength <= length && length <= maxLength;
    }
}
