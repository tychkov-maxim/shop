package com.epam.tm.shop.action;

import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.dao.jpa.JPAUserDao;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.exception.ActionException;
import com.epam.tm.shop.exception.DaoException;
import com.epam.tm.shop.exception.DaoNoDataException;
import com.epam.tm.shop.validator.NotEmptyParameterValidator;
import com.epam.tm.shop.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

import static com.epam.tm.shop.util.ConstantHolder.REDIRECT;

public class ChangeLanguageAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ChangeLanguageAction.class);

    private static final String LANGUAGE_PARAMETER = "language";
    private static final String ENGLISH_LOCALE = "en";
    private static final String RUSSIAN_LOCALE = "ru";

    @EJB
    private UserDao userDao;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {


        try {
            User byId = userDao.findById(1);
            log.trace(byId.toString());
        } catch (DaoException | DaoNoDataException e) {
            log.trace("Error");
        }

        log.trace("start to change language action");
        String language = req.getParameter(LANGUAGE_PARAMETER);
        Validator notEmptyValidator = new NotEmptyParameterValidator();

        if (notEmptyValidator.isValid(language)) {
            log.trace("language parameter is valid - {}", language);
            if (language.equals(ENGLISH_LOCALE) || language.equals(RUSSIAN_LOCALE)) {
                Config.set(req.getSession(), Config.FMT_LOCALE, new java.util.Locale(language));
                log.trace("changed language to {}", language);
            }
        }
        return REDIRECT;


    }
}
