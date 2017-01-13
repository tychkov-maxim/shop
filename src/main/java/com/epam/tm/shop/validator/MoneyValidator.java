package com.epam.tm.shop.validator;

public class MoneyValidator extends ValidatorImpl {

    private static final String DOUBLE_MONEY_PATTERN = "[0-9]{1,9}([.][0-9]{1,2}){0,1}";

    @Override
    public boolean isValid(String value) {
        RegExpValidator doubleValidator = new RegExpValidator();
        doubleValidator.setRegExp(DOUBLE_MONEY_PATTERN);
        return (doubleValidator.isValid(value) && (Double.parseDouble(value) >= 0));
    }
}
