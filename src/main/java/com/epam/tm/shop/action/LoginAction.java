package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        //// FIXME: 13.12.2016 need to validate here

        try {
            User user = checkUser(login);
            if (!user.getPassword().equals(password))
                return "password not correct";
            else
                return "Hello, " + user.getFirstName();
        } catch (ActionException e) {
            return "login not found";
        }

    }

    private User checkUser(String login) throws ActionException {
        UserService userService = new UserService();
        try {
            return userService.getUserByLogin(login);
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
