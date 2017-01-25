package com.epam.tm.shop.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.tm.shop.util.ConstantHolder.REDIRECT;

public class LogoutAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(LogoutAction.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        log.trace("start to logout");
        req.getSession(false).invalidate();
        log.trace("session was deleted");
        return REDIRECT;
    }
}
