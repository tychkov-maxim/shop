package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.ServiceNoDataException;
import com.epam.tm.shop.service.UserService;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class CheckoutAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CheckoutAction.class);

    private static final String ATTRIBUTE_ERROR_MESSAGE = "checkoutErrors";
    private static final String FORM_NAME = "checkout";
    private static final String NOT_ENOUGH_MONEY_ERROR = "not.enough.money.error.message";
    private static final String ATTRIBUTE_SESSION_USER_NAME = "user";
    private static final String ATTRIBUTE_SESSION_CART_NAME = "cart";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        log.trace("start checkout action");

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute(ATTRIBUTE_SESSION_USER_NAME);
        Cart cart = (Cart) session.getAttribute(ATTRIBUTE_SESSION_CART_NAME);

        if ((user != null) && (cart != null) && (cart.getSize() != 0)) {
            try {
                UserService userService = new UserService();
                user = userService.getUserById(user.getId());
                Money userAccount = user.getAccount();
                session.setAttribute(ATTRIBUTE_SESSION_USER_NAME, user);
                log.trace("user in session was updated");
                if (userAccount.isLessThan(cart.getAllCost())) {
                    List<String> errorMessage = new ArrayList<>();
                    errorMessage.add(NOT_ENOUGH_MONEY_ERROR);
                    req.setAttribute(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                    log.trace("not enough money on account");
                }
            } catch (ServiceNoDataException | ServiceException e) {
                throw new ActionException(e);
            }
        }

        return FORM_NAME;
    }
}
