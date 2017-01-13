package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.ServiceExceptionError;
import com.epam.tm.shop.service.UserService;
import com.epam.tm.shop.validator.FormValidator;
import com.epam.tm.shop.validator.FormValidatorFactory;
import com.epam.tm.shop.validator.ValidatorException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class UserRegisterAction implements Action {

    public static final Logger log = LoggerFactory.getLogger(LoginAction.class);
    private static final String FORM_NAME = "register";
    private static final String REGISTER_ERROR_PARAMETER = "registerError";
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASS_PARAMETER = "passwd";
    private static final String REGISTER_SUCCESS = "register-success";
    private static final String ATTRIBUTE_SESSION_USER_NAME = "user";
    private static final String FIRST_NAME_PARAMETER = "first_name";
    private static final String SECOND_NAME_PARAMETER = "second_name";
    private static final String ADDRESS_PARAMETER = "address";
    private static final String LOGIN_PREVIOUS_VALUE = "regLogin";
    private static final String FIRST_NAME_PREVIOUS_VALUE = "regFirstName";
    private static final String SECOND_NAME_PREVIOUS_VALUE = "regSecondName";
    private static final String ADDRESS_PREVIOUS_VALUE = "regAddress";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        log.trace("start to register user");
        String login = req.getParameter(LOGIN_PARAMETER);
        String passwd = req.getParameter(PASS_PARAMETER);
        String firstName = req.getParameter(FIRST_NAME_PARAMETER);
        String secondName = req.getParameter(SECOND_NAME_PARAMETER);
        String address = req.getParameter(ADDRESS_PARAMETER);

        try {
            FormValidator registerValidator = FormValidatorFactory.getFormValidatorByNameOfForm(FORM_NAME);
            if (registerValidator.validate(req)) {
                req.setAttribute(LOGIN_PREVIOUS_VALUE, login);
                req.setAttribute(FIRST_NAME_PREVIOUS_VALUE, firstName);
                req.setAttribute(SECOND_NAME_PREVIOUS_VALUE, secondName);
                req.setAttribute(ADDRESS_PREVIOUS_VALUE, address);
                log.trace("register form's parameters are not valid");
                return FORM_NAME;
            }
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }


        User user = new User(login, passwd, firstName, secondName, Role.getUserRole(), Money.of(CurrencyUnit.USD, 0), address);
        UserService userService = new UserService();
        List<String> errorMessage = new ArrayList<>();
        try {
            User savedUser = userService.register(user);
            log.trace("user {} with id {} was registered successfully", savedUser.getLogin(), savedUser.getId());
            HttpSession session = req.getSession(true);
            session.setAttribute(ATTRIBUTE_SESSION_USER_NAME, user);
            return REGISTER_SUCCESS;
        } catch (ServiceExceptionError e) {
            log.trace("user {} already exist", user.getLogin());
            errorMessage.add(e.getMessage());
            req.setAttribute(REGISTER_ERROR_PARAMETER, errorMessage);
            return FORM_NAME;
        } catch (ServiceException e) {
            log.trace("user registration was failed");
            throw new ActionException(e);
        }
    }
}
