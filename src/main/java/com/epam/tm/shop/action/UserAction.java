package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.exception.ActionException;
import com.epam.tm.shop.exception.ServiceException;
import com.epam.tm.shop.exception.ServiceNoDataException;
import com.epam.tm.shop.exception.ServiceNonUniqueFieldException;
import com.epam.tm.shop.service.UserService;
import com.epam.tm.shop.validator.MoneyValidator;
import com.epam.tm.shop.validator.NotEmptyParameterValidator;
import com.epam.tm.shop.validator.Validator;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.epam.tm.shop.util.ConstantHolder.ATTRIBUTE_SESSION_USER_NAME;
import static com.epam.tm.shop.util.ConstantHolder.LOGIN_PARAMETER;

public class UserAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(UserAction.class);

    private static final String FORM_NAME = "profile";
    private static final String USER_ERROR_PARAMETER = "profileMessages";
    private static final String USER_ERROR_MESSAGE = "user.not.found";
    private static final String USER_MONEY_PARAMETER = "money";
    private static final String USER_ADMIN_PARAMETER = "admin";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        log.trace("start user action");

        List<String> errorMessage = new ArrayList<>();
        Validator notEmptyParameterValidator = new NotEmptyParameterValidator();
        Validator moneyValidator = new MoneyValidator();
        String loginParam = req.getParameter(LOGIN_PARAMETER);


        if (notEmptyParameterValidator.isValid(loginParam)) {
            log.trace("login parameter is valid", loginParam);
            UserService userService = new UserService();
            try {
                User user = userService.getUserByLogin(loginParam);
                log.trace("got user {}", user.getLogin());
                String moneyParam = req.getParameter(USER_MONEY_PARAMETER);
                String adminParam = req.getParameter(USER_ADMIN_PARAMETER);
                if (moneyValidator.isValid(moneyParam)) {
                    double money = Double.parseDouble(moneyParam);
                    if (money >= 0) {
                        user.setAccount(Money.of(CurrencyUnit.USD, money));
                        user = userService.saveUser(user);
                        log.trace("change account of user {} to {}", user.getLogin(), user.getAccount());
                    }
                } else if (notEmptyParameterValidator.isValid(adminParam)) {
                    user.setRole(Role.getAdministratorRole());
                    user = userService.saveUser(user);
                    log.trace("change user {} status to administrator", user.getLogin());
                }
                req.setAttribute(ATTRIBUTE_SESSION_USER_NAME, user);
            } catch (ServiceException | ServiceNonUniqueFieldException e) {
                throw new ActionException(e);
            } catch (ServiceNoDataException e) {
                log.trace("user wasn't found");
                errorMessage.add(USER_ERROR_MESSAGE);
                req.setAttribute(USER_ERROR_PARAMETER, errorMessage);
            }
        } else {
            log.trace("login parameter not correct");
            errorMessage.add(USER_ERROR_MESSAGE);
            req.setAttribute(USER_ERROR_PARAMETER, errorMessage);
        }
        return FORM_NAME;
    }
}
