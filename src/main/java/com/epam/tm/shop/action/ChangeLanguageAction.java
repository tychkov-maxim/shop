package com.epam.tm.shop.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

public class ChangeLanguageAction implements Action {

    public static final Logger log = LoggerFactory.getLogger(ChangeLanguageAction.class);

    private static final String LANGUAGE_PARAMETER = "language";
    private static final String REFERRER_PARAMETER = "referrer";
    private static final String REDIRECT = "redirect:";
    private static final String ENGLISH_LOCALE = "en";
    private static final String RUSSIAN_LOCALE= "ru";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        String language = req.getParameter(LANGUAGE_PARAMETER);

        if ((language != null) && (!language.equals(""))){
            if (language.equals(ENGLISH_LOCALE) || language.equals(RUSSIAN_LOCALE))
                Config.set(req.getSession(), Config.FMT_LOCALE, new java.util.Locale(language));
        }
        String referrer = req.getParameter(REFERRER_PARAMETER);
        if ((referrer == null) || referrer.equals("")) referrer = "/";
        return REDIRECT+referrer;
    }
}
