package com.epam.tm.shop.validator;

import java.util.List;
import java.util.Map;

public class RegisterValidator extends FormValidator {

    public RegisterValidator(Map<String, List<Validator>> filedValidators) {
        super(filedValidators);
    }
}
