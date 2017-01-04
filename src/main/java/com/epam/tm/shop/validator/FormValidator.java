package com.epam.tm.shop.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class FormValidator {

    private static final Logger log = LoggerFactory.getLogger(FormValidator.class);
    private static final String ERROR_POSTFIX = "Errors";
    private Map<String,List<Validator>> fieldValidators;


    public FormValidator(Map<String, List<Validator>> fieldValidators) {
        this.fieldValidators = fieldValidators;
        log.trace("FormValidator with {} fields was created",fieldValidators.size());
    }

    public boolean validate(HttpServletRequest request){
        Map<String, List<String>> errors = getAllErrorsFromRequest(request);
        boolean hasError = false;

        for (Map.Entry<String, List<Validator>> entry : fieldValidators.entrySet()) {
            request.getSession().removeAttribute(entry.getKey()+ERROR_POSTFIX);
        }

        for (Map.Entry<String, List<String>> entry : errors.entrySet()) {
            if (entry.getValue().size() > 0){
                request.getSession().setAttribute(entry.getKey()+ERROR_POSTFIX,entry.getValue());
                hasError = true;
            }
        }

        return hasError;

    }

    private Map<String,List<String>> getAllErrorsFromRequest(HttpServletRequest request){

        Map<String,List<String>> errors = new HashMap<>();

        for (Map.Entry<String, List<Validator>> entry : fieldValidators.entrySet()) {
            List<String> errorsOfField = new ArrayList<>();
            String field = entry.getKey();
            String fieldValue = request.getParameter(field);
            List<Validator> validators = entry.getValue();
            log.trace("field: {} start to validate",field);
            for (Validator validator : validators) {
                if (!validator.isValid(fieldValue)){
                    errorsOfField.add(validator.getMessage());
                }
            }
            log.trace("field: {} was finished to validate with {} errors",field,errorsOfField.size());
            errors.put(field,errorsOfField);
        }
        return errors;
    }


 }
