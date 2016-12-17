package com.epam.tm.shop.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormValidator {

    private static final Logger log = LoggerFactory.getLogger(FormValidator.class);
    private Map<String,List<Validator>> fieldValidators;


    public FormValidator(Map<String, List<Validator>> fieldValidators) {
        this.fieldValidators = fieldValidators;
        log.trace("FormValidator with {} fields was created",fieldValidators.size());
    }

    public Map<String,List<String>> validate(HttpServletRequest request){

        Map<String,List<String>> errors = new HashMap<>();

        for (Map.Entry<String, List<Validator>> entry : fieldValidators.entrySet()) {
            List<String> errorsOfFiled = new ArrayList<>();
            String field = entry.getKey();
            String fieldValue = request.getParameter(field);
            List<Validator> validators = entry.getValue();
            log.trace("field: {} start to validate",field);
            for (Validator validator : validators) {
                if (!validator.isValid(fieldValue)){
                    errorsOfFiled.add(validator.getMessage());
                }
            }
            log.trace("field: {} was finished  to validate with {} errors",field,errorsOfFiled.size());
            errors.put(field,errorsOfFiled);
        }
        return errors;
    }


 }
