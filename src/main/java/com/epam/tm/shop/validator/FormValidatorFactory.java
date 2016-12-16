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

        return new FormValidator(readPropertyOfFormByName(nameOfForm));
    }

    private Map<String,List<Validator>> readPropertyOfFormByName(String nameOfForm) throws ValidatorException {
        Map<String,List<Validator>> filedValidators = new HashMap<>();
        try {

            PropertyManager propertyManager = new PropertyManager(PROPERTIES_FILE_NAME);
            String validator = propertyManager.getPropertyKey(PREFIX + nameOfForm + ".1");
            int minLength = propertyManager.getIntPropertyKey(PREFIX + nameOfForm + ".1.minLength");
            int maxLength = propertyManager.getIntPropertyKey(PREFIX + nameOfForm + ".1.maxLength");
            String errorMess = propertyManager.getPropertyKey(PREFIX + nameOfForm + ".1.errorMessage");

        } catch (PropertyManagerException e) {
            throw new ValidatorException(e);
        }
        return filedValidators;
    }
}
