package com.epam.tm.shop.validator;

import com.epam.tm.shop.exception.ValidatorException;

public class FormValidatorFactory {

    private static final String PROPERTIES_FILE_NAME = "validator.properties";

    public static FormValidator getFormValidatorByNameOfForm(String nameOfForm) throws ValidatorException {
        PropertyReaderOfValidator propertyReaderOfValidator = new PropertyReaderOfValidator(PROPERTIES_FILE_NAME);
        return new FormValidator(propertyReaderOfValidator.readPropertiesOfFormValidatorByName(nameOfForm));
    }


}
