package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.UserService;
import com.epam.tm.shop.validator.FormValidator;
import com.epam.tm.shop.validator.FormValidatorFactory;
import com.epam.tm.shop.validator.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class LoginAction implements Action {

    public static final Logger log = LoggerFactory.getLogger(LoginAction.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

            String error = "";
        try {
            FormValidator loginValidator = FormValidatorFactory.getFormValidatorByNameOfForm("login");
            Map<String, List<String>> errors = loginValidator.validate(req);

            for (Map.Entry<String, List<String>> stringListErrors : errors.entrySet()) {
               error = error + "\n" + stringListErrors.getKey();
                for (String s : stringListErrors.getValue()) {
                    error = error + " " + s;
                }
            }
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }


        try {
            User user = checkUser(login);
            if (!user.getPassword().equals(password))
                return "password not correct" + error;
            else
                return "Hello, " + user.getFirstName() + error;
        } catch (ActionException e) {
            return "login not found" + error;
        }



    }

    private User checkUser(String login) throws ActionException {
        UserService userService = new UserService();
        try {
            return userService.getUserByLogin(login);
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
