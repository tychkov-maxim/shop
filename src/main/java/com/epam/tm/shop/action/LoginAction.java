package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.UserService;
import com.epam.tm.shop.validator.FormValidator;
import com.epam.tm.shop.validator.FormValidatorFactory;
import com.epam.tm.shop.validator.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;

public class LoginAction implements Action {

    public static final Logger log = LoggerFactory.getLogger(LoginAction.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Map<String, List<String>> errors;


        try {
            FormValidator loginValidator = FormValidatorFactory.getFormValidatorByNameOfForm("login");
            if (loginValidator.validate(req)) {
                log.debug("not valid");
                return "redirect:/";
            }
        } catch (ValidatorException e) {
            throw new ActionException(e);
        }

        List<String> dataError = new ArrayList<>();
        try {
            User user = checkUser(login);
            if (!user.getPassword().equals(password)){
                dataError.add("Incorrect password");
                req.setAttribute("mainError",dataError);
                log.debug("incorrect password");
                return "redirect:/";
            }
            else {
                HttpSession session = req.getSession(true);
                session.setAttribute("User",user);
                log.debug("success");
                return "login-success";
            }
        } catch (ActionException e) {
            dataError.add("User not found");
            req.setAttribute("mainError",dataError);
            log.debug("User not found");
            return "redirect:/";
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
