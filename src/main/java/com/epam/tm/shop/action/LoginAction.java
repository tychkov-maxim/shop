package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.ServiceNoDataException;
import com.epam.tm.shop.service.UserService;
import com.epam.tm.shop.validator.FormValidator;
import com.epam.tm.shop.validator.FormValidatorFactory;
import com.epam.tm.shop.validator.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginAction implements Action {

    public static final Logger log = LoggerFactory.getLogger(LoginAction.class);
    private static final String LOGIN_ERROR = "loginError";
    private static final String LOGIN = "login";
    private static final String PASS_PARAMETER = "password";
    private static final String REDIRECT = "redirect:/";
    private static final String LOGIN_SUCCESS = "redirect:/login-success";
    private static final String INCORRECT_PASSWORD = "incorrect.pass";
    private static final String USER_NOT_FOUND = "user.not.found";
    private static final String ATTRIBUTE_SESSION_USER_NAME = "user";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        log.trace("start to authorize");

        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASS_PARAMETER);


        List<String> dataError = new ArrayList<>();

        try {
            User user = checkUser(login);
            if (!user.getPassword().equals(password)){
                dataError.add(INCORRECT_PASSWORD);
                req.setAttribute(LOGIN_ERROR,dataError);
                log.trace("incorrect password with login {}",user.getLogin());
                return REDIRECT;
            }
            else {
                HttpSession session = req.getSession(true);
                session.setAttribute(ATTRIBUTE_SESSION_USER_NAME,user);
                log.trace("login success as {} with id {}",user.getLogin(),user.getId());
                return LOGIN_SUCCESS;
            }
        } catch (ServiceNoDataException e) {
            dataError.add(USER_NOT_FOUND);
            req.setAttribute(LOGIN_ERROR,dataError);
            log.trace("User not found with login {}", login);
            return REDIRECT;
        }

    }

    private User checkUser(String login) throws ActionException, ServiceNoDataException {
        UserService userService = new UserService();
        try {
            return userService.getUserByLogin(login);
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
