package com.epam.tm.shop.action;

import com.epam.tm.shop.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

    String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException;

}
