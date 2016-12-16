package com.epam.tm.shop.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpValidator extends ValidatorImpl {
    private Pattern pattern;

    public RegExpValidator(String regExp) {
        pattern = Pattern.compile(regExp);
    }

    @Override
    public boolean isValid(String value) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
