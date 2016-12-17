package com.epam.tm.shop.validator;

import com.epam.tm.shop.util.PropertyManager;
import com.epam.tm.shop.util.PropertyManagerException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormValidatorFactory {

    private static final String PROPERTIES_FILE_NAME = "validator.properties";
    private static final String PREFIX = "form.";

    public FormValidator getFormValidatorByNameOfForm(String nameOfForm) throws ValidatorException {
        PropertyReaderOfValidator propertyReaderOfValidator = new PropertyReaderOfValidator(PROPERTIES_FILE_NAME);
        return new FormValidator(propertyReaderOfValidator.readPropertiesOfFormValidatorByName(nameOfForm));
    }


}
