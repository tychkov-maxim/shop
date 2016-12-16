package com.epam.tm.shop.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormValidator {

    private Map<String,List<Validator>> filedValidators;


    public FormValidator(Map<String, List<Validator>> filedValidators) {
        this.filedValidators = filedValidators;
    }

    public Map<String,List<String>> validate(HttpServletRequest request){

        Map<String,List<String>> errors = new HashMap<>();

        for (Map.Entry<String, List<Validator>> entry : filedValidators.entrySet()) {
            List<String> errorsOfFiled = new ArrayList<>();
            String field = entry.getKey();
            String fieldValue = request.getParameter(field);
            List<Validator> validators = entry.getValue();
            for (Validator validator : validators) {
                if (!validator.isValid(fieldValue)){
                    errorsOfFiled.add(validator.getMessage());
                }
            }
            errors.put(field,errorsOfFiled);
        }
        return errors;
    }


 }
