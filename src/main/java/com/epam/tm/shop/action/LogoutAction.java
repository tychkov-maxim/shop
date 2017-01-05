package com.epam.tm.shop.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action{

    public static final Logger log = LoggerFactory.getLogger(LogoutAction.class);
    private static final String REDIRECT = "redirect:/";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        req.getSession(false).invalidate();
        return REDIRECT;
    }
}
