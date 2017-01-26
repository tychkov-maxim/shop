package com.epam.tm.shop.validator;

import com.epam.tm.shop.exception.PropertyManagerException;
import com.epam.tm.shop.exception.ValidatorException;
import com.epam.tm.shop.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

public class PropertyReaderOfValidator {

    private static final Logger log = LoggerFactory.getLogger(PropertyReaderOfValidator.class);
    private static final String DOT_REGEX = "[.]";
    private static final String FORM = "form";
    private static final int NUMBER_OF_TYPE_OF_VALIDATOR = 0;
    private static final int NUMBER_OF_NAME_OF_FORM = 1;
    private static final int NUMBER_OF_NAME_OF_FIELD = 2;
    private static final int NUMBER_OF_NUMBER_OF_VALIDATOR = 3;
    private static final int NUMBER_OF_PROPERTY_OF_VALIDATOR = 4;


    private PropertyManager propertyManager;

    public PropertyReaderOfValidator(String fileName) throws ValidatorException {
        try {
            propertyManager = new PropertyManager(fileName);
            log.trace("PropertyReaderOfValidator was created successfully");
        } catch (PropertyManagerException e) {
            throw new ValidatorException(e);
        }
    }

    public Map<String, List<Validator>> readPropertiesOfFormValidatorByName(String nameOfForm) throws ValidatorException {
        log.trace("start to read properties of form validator by name of form: {}", nameOfForm);
        Map<String, List<Validator>> fieldValidators = new HashMap<>();
        Set<String> allFormFields = getAllFormFields(nameOfForm);
        for (String formField : allFormFields) {
            fieldValidators.put(formField, getValidatorsForField(nameOfForm, formField));
        }

        log.trace("reading properties of from validator by name of form {} was finished. got validators for {} fields", nameOfForm, fieldValidators.size());
        return fieldValidators;
    }

    private Set<String> getAllFormFields(String nameOfForm) {
        log.trace("start to get all fields for form {}", nameOfForm);
        Set<String> allFields = new HashSet<>();
        Enumeration<String> allPropertyKeys = (Enumeration<String>) propertyManager.getPropertyNames();
        while (allPropertyKeys.hasMoreElements()) {
            String key = allPropertyKeys.nextElement();
            String[] splittedKey = key.split(DOT_REGEX);
            // form. the second is name of form, the third is filed of form
            if (splittedKey.length > NUMBER_OF_NAME_OF_FORM) {
                if (splittedKey[NUMBER_OF_TYPE_OF_VALIDATOR].equals(FORM))
                    if (splittedKey[NUMBER_OF_NAME_OF_FORM].equals(nameOfForm))
                        allFields.add(splittedKey[NUMBER_OF_NAME_OF_FIELD]);
            }
        }
        log.trace("form {} has {} fields", nameOfForm, allFields.size());

        return allFields;
    }

    private List<Validator> getValidatorsForField(String nameOfForm, String field) throws ValidatorException {

        log.trace("start to get all validators for field: {}", field);

        List<Validator> validators = new ArrayList<>();


        Map<String, Integer> validatorsWithNumbers = getValidatorsWithNumbers(nameOfForm, field);

        for (Map.Entry<String, Integer> entry : validatorsWithNumbers.entrySet()) {
            Map<String, String> allPropertiesOfValidatorByNumber = getAllPropertiesOfValidatorByNumber(nameOfForm, field, entry.getValue());
            validators.add(createValidatorWithProperties(entry.getKey(), allPropertiesOfValidatorByNumber));
        }

        log.trace("got {} validators for field : {}", validators.size(), field);
        return validators;
    }

    private Map<String, Integer> getValidatorsWithNumbers(String nameOfForm, String field) throws ValidatorException {
        Map<String, Integer> validators = new HashMap<>();

        Enumeration<String> allPropertyKeys = (Enumeration<String>) propertyManager.getPropertyNames();

        while (allPropertyKeys.hasMoreElements()) {
            String key = allPropertyKeys.nextElement();
            String[] splittedKey = key.split(DOT_REGEX);


            if (splittedKey.length == NUMBER_OF_NUMBER_OF_VALIDATOR + 1) {
                if (splittedKey[NUMBER_OF_TYPE_OF_VALIDATOR].equals(FORM))
                    if (splittedKey[NUMBER_OF_NAME_OF_FORM].equals(nameOfForm))
                        if (splittedKey[NUMBER_OF_NAME_OF_FIELD].equals(field)) {

                            Integer number = Integer.parseInt(splittedKey[NUMBER_OF_NUMBER_OF_VALIDATOR]);
                            try {
                                validators.put(propertyManager.getPropertyKey(key), number);
                            } catch (PropertyManagerException e) {
                                throw new ValidatorException(e);
                            }

                        }
            }
        }

        return validators;
    }


    private Map<String, String> getAllPropertiesOfValidatorByNumber(String nameOfForm, String field, int number) throws ValidatorException {
        Map<String, String> properties = new HashMap<>();
        String property;
        Enumeration<String> allPropertyKeys = (Enumeration<String>) propertyManager.getPropertyNames();

        while (allPropertyKeys.hasMoreElements()) {
            String key = allPropertyKeys.nextElement();
            String[] splittedKey = key.split(DOT_REGEX);


            if (splittedKey.length > NUMBER_OF_PROPERTY_OF_VALIDATOR) {
                if (splittedKey[NUMBER_OF_TYPE_OF_VALIDATOR].equals(FORM))
                    if (splittedKey[NUMBER_OF_NAME_OF_FORM].equals(nameOfForm))
                        if (splittedKey[NUMBER_OF_NAME_OF_FIELD].equals(field))
                            if (Integer.parseInt(splittedKey[NUMBER_OF_NUMBER_OF_VALIDATOR]) == number) {
                                property = splittedKey[NUMBER_OF_PROPERTY_OF_VALIDATOR];
                                try {
                                    properties.put(property, propertyManager.getPropertyKey(key));
                                } catch (PropertyManagerException e) {
                                    throw new ValidatorException(e);
                                }
                            }

            }
        }

        return properties;
    }

    private Validator createValidatorWithProperties(String validatorPath, Map<String, String> properties) throws ValidatorException {
        log.trace("start to create validator {}", validatorPath);
        try {
            Class<?> aClass = Class.forName(validatorPath);
            Validator validator = (Validator) aClass.newInstance();
            log.trace("{} validator was created successfully", validatorPath);
            return setFieldsToValidator(validator, properties);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new ValidatorException(MessageFormat.format("creating validator {0} was failed", validatorPath), e);
        }
    }

    private Validator setFieldsToValidator(Validator validator, Map<String, String> properties) throws ValidatorException {
        try {
            log.trace("start to set fields to validator {}", validator);
            BeanInfo beanInfo = Introspector.getBeanInfo(validator.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (writeMethod != null) {
                    String property = properties.get(writeMethod.getName().substring(3).toLowerCase());
                    if (property != null) {
                        try {
                            if (writeMethod.getParameterTypes()[0].getName().equals("int"))
                                writeMethod.invoke(validator, Integer.parseInt(property));
                            else
                                writeMethod.invoke(validator, property);
                            log.trace("set property: {} by write method: {} was finished successfully", property, writeMethod.getName());
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new ValidatorException("setting field to validator was failed", e);
                        }
                    }
                }

            }
            log.trace("finish to set fields to validator {}", validator);
            return validator;
        } catch (IntrospectionException e) {
            throw new ValidatorException(e);
        }
    }


}
