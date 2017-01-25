package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Order;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.service.OrderService;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.service.ServiceNoDataException;
import com.epam.tm.shop.validator.NotEmptyParameterValidator;
import com.epam.tm.shop.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.epam.tm.shop.util.ConstantHolder.*;

public class GetOrdersAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(GetOrdersAction.class);
    private static final String FORM_NAME = "show-orders";
    private static final String NO_ONE_ORDER_MESSAGE = "no.one.order";
    private static final String NO_ONE_COMPLETED_ORDER_MESSAGE = "no.one.completed.order";
    private static final String NO_ONE_SHIPPING_ORDER_MESSAGE = "no.one.shipping.order";
    private static final String NO_ONE_PROCESSING_ORDER_MESSAGE = "no.one.processing.order";
    private static final String PRODUCT_MESSAGE_ATTRIBUTE = "ordersMessages";
    private static final String ALL_ORDERS_PROCESSING_VALUE_OF_STATUS = "all";
    private static final String ORDERS = "Orders";
    private static final String PERMISSION_REDIRECT = "redirect:/permission.do";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        log.trace("start to get order action");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_SESSION_USER_NAME);

        Validator notEmptyParameterValidator = new NotEmptyParameterValidator();
        String statusPar = req.getParameter(STATUS_PARAMETER);
        List<String> message = new ArrayList<>();

        if (!notEmptyParameterValidator.isValid(statusPar)) {
            log.trace("parameter not valid - {}", statusPar);
            message.add(NO_ONE_ORDER_MESSAGE);
            req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE, message);
            return FORM_NAME;
        }

        OrderService orderService = new OrderService();
        try {
            switch (statusPar) {
                case PROCESSING_ORDERS_VALUE_OF_STATUS:
                    try {
                        log.trace("trying to get all processing orders of user {}", user.getLogin());
                        List<Order> userProcessOrders = orderService.getUserOrdersByOrderStatus(user.getId(), OrderStatus.getProcessingStatus());
                        req.setAttribute(ORDERS, userProcessOrders);
                        log.trace("got {} orders", userProcessOrders.size());
                    } catch (ServiceNoDataException e) {
                        message.add(NO_ONE_PROCESSING_ORDER_MESSAGE);
                        req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE, message);
                        log.trace("no one orders");
                    }

                    break;
                case COMPLETED_VALUE_OF_STATUS:
                    try {
                        log.trace("trying to get all completed orders of user {}", user.getLogin());
                        List<Order> userComplettedOrders = orderService.getUserOrdersByOrderStatus(user.getId(), OrderStatus.getCompletedStatus());
                        req.setAttribute(ORDERS, userComplettedOrders);
                        log.trace("got {} orders", userComplettedOrders.size());
                    } catch (ServiceNoDataException e) {
                        message.add(NO_ONE_COMPLETED_ORDER_MESSAGE);
                        req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE, message);
                        log.trace("no one orders");
                    }

                    break;
                case SHIPPING_ORDERS_VALUE_OF_STATUS:
                    try {
                        log.trace("trying to get all shipping orders of user {}", user.getLogin());
                        List<Order> userShippOrders = orderService.getUserOrdersByOrderStatus(user.getId(), OrderStatus.getShippingStatus());
                        req.setAttribute(ORDERS, userShippOrders);
                        log.trace("got {} orders", userShippOrders.size());
                    } catch (ServiceNoDataException e) {
                        message.add(NO_ONE_SHIPPING_ORDER_MESSAGE);
                        req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE, message);
                        log.trace("no one orders");
                    }
                    break;
                case ALL_ORDERS_PROCESSING_VALUE_OF_STATUS:
                    try {
                        if (user.getRole().equals(Role.getAdministratorRole())) {
                            log.trace("trying to get all processing orders");
                            List<Order> userShippOrders = orderService.getAllOrdersByOrderStatus(OrderStatus.getProcessingStatus());
                            req.setAttribute(ORDERS, userShippOrders);
                            log.trace("got {} orders", userShippOrders.size());
                        } else
                            return PERMISSION_REDIRECT;
                    } catch (ServiceNoDataException e) {
                        message.add(NO_ONE_SHIPPING_ORDER_MESSAGE);
                        req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE, message);
                        log.trace("no one orders");
                    }
                    break;
                default:
                    message.add(NO_ONE_ORDER_MESSAGE);
                    req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE, message);
                    log.trace("no one orders");
                    break;
            }

        } catch (ServiceException e) {
            throw new ActionException(e);
        }

        log.trace("get order action was finished");
        return FORM_NAME;

    }
}
