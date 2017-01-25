package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.Order;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.service.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(OrderAction.class);

    private static final String ATTRIBUTE_SESSION_USER_NAME = "user";
    private static final String ATTRIBUTE_SESSION_CART_NAME = "cart";

    private static final String CHECKOUT_REDIRECT = "redirect:/checkout.do";
    private static final String ROOT_REDIRECT = "redirect:/";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        log.trace("start order action");
        OrderService orderService = new OrderService();
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute(ATTRIBUTE_SESSION_USER_NAME);
        Cart cart = (Cart) session.getAttribute(ATTRIBUTE_SESSION_CART_NAME);

        if ((user == null) || (cart == null) || (cart.getSize() == 0))
            throw new ActionException("Sorry, an error was occurred");

        Order order = new Order(cart, user, DateTime.now(), cart.getAllCost(), OrderStatus.getProcessingStatus());

        try {
            Order savedOrder = orderService.makeOrder(order);
            log.trace("order was ordered successfully with id:{}", savedOrder.getId());
            session.setAttribute(ATTRIBUTE_SESSION_CART_NAME, new Cart());
            session.setAttribute(ATTRIBUTE_SESSION_USER_NAME, updateUser(user));
            return ROOT_REDIRECT;
        } catch (ServiceException | ServiceNonUniqueFieldException | ServiceNoDataException e) {
            throw new ActionException(e);
        } catch (ServiceExceptionError serviceExceptionError) {
            return CHECKOUT_REDIRECT;
        }
    }

    private User updateUser(User user) throws ServiceNoDataException, ServiceException {
        UserService userService = new UserService();
        return userService.getUserById(user.getId());
    }
}
