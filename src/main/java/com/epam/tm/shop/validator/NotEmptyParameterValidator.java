package com.epam.tm.shop.validator;

public class NotEmptyParameterValidator extends ValidatorImpl {
    @Override
    public boolean isValid(String value) {
        return ((value != null) && (!value.equals("")));
    }
}
