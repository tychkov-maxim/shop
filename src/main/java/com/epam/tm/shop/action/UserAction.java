package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.ServiceNoDataException;
import com.epam.tm.shop.service.ServiceNonUniqueFieldException;
import com.epam.tm.shop.service.UserService;
import com.epam.tm.shop.validator.NotEmptyParameterValidator;
import com.epam.tm.shop.validator.OnlyNumberValidator;
import com.epam.tm.shop.validator.Validator;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class UserAction implements Action {

    public static final Logger log = LoggerFactory.getLogger(UserAction.class);

    private static final String ATTRIBUTE_USER_NAME = "user";
    private static final String FORM_NAME = "profile";
    private static final String USER_ERROR_PARAMETER = "profileMessages";
    private static final String USER_ERROR_MESSAGE = "user.not.found";
    private static final String USER_LOGIN_PARAMETER = "login";
    private static final String USER_MONEY_PARAMETER = "money";
    private static final String USER_ADMIN_PARAMETER = "admin";

    // FIXME: 12.01.2017 add logging
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        List<String> errorMessage = new ArrayList<>();
        Validator notEmptyParameterValidator = new NotEmptyParameterValidator();
        Validator onlyNumberValidator = new OnlyNumberValidator();
        String loginParam = req.getParameter(USER_LOGIN_PARAMETER);


        if (notEmptyParameterValidator.isValid(loginParam)) {

            UserService userService = new UserService();
            try {
                User user = userService.getUserByLogin(loginParam);

                String moneyParam  = req.getParameter(USER_MONEY_PARAMETER);
                String adminParam = req.getParameter(USER_ADMIN_PARAMETER);
                if (onlyNumberValidator.isValid(moneyParam)){
                    int money = Integer.parseInt(moneyParam);
                    user.setAccount(Money.of(CurrencyUnit.USD, money));
                    user = userService.saveUser(user);
                } else if (notEmptyParameterValidator.isValid(adminParam)){
                    user.setRole(Role.getAdministratorRole());
                    user = userService.saveUser(user);
                }
                req.setAttribute(ATTRIBUTE_USER_NAME, user);
            } catch (ServiceException | ServiceNonUniqueFieldException e) {
                throw new ActionException(e);
            } catch (ServiceNoDataException e) {
                errorMessage.add(USER_ERROR_MESSAGE);
                req.setAttribute(USER_ERROR_PARAMETER, errorMessage);
            }
        } else {
            errorMessage.add(USER_ERROR_MESSAGE);
            req.setAttribute(USER_ERROR_PARAMETER, errorMessage);
        }
        return FORM_NAME;
    }
}
