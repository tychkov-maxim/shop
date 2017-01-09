package com.epam.tm.shop.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageAction implements Action {

    private String pageAction;

    public ShowPageAction(String pageAction) {
        this.pageAction = pageAction;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        return pageAction;
    }
}
