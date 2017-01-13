package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.ServiceNoDataException;
import com.epam.tm.shop.service.UserService;
import com.epam.tm.shop.util.HashGenerator;
import com.epam.tm.shop.util.HashGeneratorException;
import com.epam.tm.shop.util.MD5Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class LoginAction implements Action {

    public static final Logger log = LoggerFactory.getLogger(LoginAction.class);
    private static final String LOGIN_ERROR_PARAMETER = "loginError";
    private static final String LOGIN_PARAMETER = "login";
    private static final String FORM_NAME = "login";
    private static final String PASS_PARAMETER = "password";
    private static final String LOGIN_SUCCESS = "login-success";
    private static final String INCORRECT_PASSWORD = "incorrect.pass";
    private static final String USER_NOT_FOUND = "user.not.found";
    private static final String ATTRIBUTE_SESSION_USER_NAME = "user";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        log.trace("start to authorize");

        String login = req.getParameter(LOGIN_PARAMETER);
        String password = req.getParameter(PASS_PARAMETER);

        List<String> errorMessage = new ArrayList<>();

        try {
            User user = checkUser(login);
            String hashedPassword = user.getPassword();
            HashGenerator hashGenerator = new MD5Generator();
            if (!hashGenerator.isHashAndParameterEquals(hashedPassword,password)){
                errorMessage.add(INCORRECT_PASSWORD);
                req.setAttribute(LOGIN_ERROR_PARAMETER,errorMessage);
                log.trace("incorrect password with login {}",user.getLogin());
                return FORM_NAME;
            }
            else {
                HttpSession session = req.getSession(true);
                session.setAttribute(ATTRIBUTE_SESSION_USER_NAME,user);
                log.trace("login success as {} with id {}",user.getLogin(),user.getId());
                return LOGIN_SUCCESS;
            }
        } catch (ServiceNoDataException e) {
            errorMessage.add(USER_NOT_FOUND);
            req.setAttribute(LOGIN_ERROR_PARAMETER,errorMessage);
            log.trace("User not found with login {}", login);
            return FORM_NAME;
        } catch (HashGeneratorException e){
            throw new ActionException(e);
        }

    }

    private User checkUser(String login) throws ActionException, ServiceNoDataException {
        UserService userService = new UserService();
        try {
            return userService.getUserByLogin(login);
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }
}
