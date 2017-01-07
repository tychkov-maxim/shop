package com.epam.tm.shop.validator;


public class OnlyNumberValidator extends RegExpValidator {

    private static final String ONLY_NUMBER_REGEX = "[0-9]+";
    private static final String ONLY_NUMBER_INVALID_MESSAGE_ = "validator.only.number";

    public OnlyNumberValidator() {
        setRegExp(ONLY_NUMBER_REGEX);
        setMessage(ONLY_NUMBER_INVALID_MESSAGE_);
    }
}
