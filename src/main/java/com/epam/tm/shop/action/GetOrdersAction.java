package com.epam.tm.shop.action;

import com.epam.tm.shop.entity.Order;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.exception.ActionException;
import com.epam.tm.shop.service.OrderService;
import com.epam.tm.shop.exception.ServiceException;
import com.epam.tm.shop.exception.ServiceNoDataException;
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

    private OrderService orderService;
    private User user;
    private List<String> message;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        log.trace("start to get order action");
        HttpSession session = req.getSession();
        user = (User) session.getAttribute(ATTRIBUTE_SESSION_USER_NAME);

        Validator notEmptyParameterValidator = new NotEmptyParameterValidator();
        String statusPar = req.getParameter(STATUS_PARAMETER);
        message = new ArrayList<>();

        if (!notEmptyParameterValidator.isValid(statusPar)) {
            log.trace("parameter not valid - {}", statusPar);
            message.add(NO_ONE_ORDER_MESSAGE);
            req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE, message);
            return FORM_NAME;
        }

        orderService = new OrderService();

        switch (statusPar) {
            case PROCESSING_ORDERS_VALUE_OF_STATUS:
                setAllUserOrdersByOrderStatusToAttributes(OrderStatus.getProcessingStatus(), NO_ONE_PROCESSING_ORDER_MESSAGE, req);
                break;
            case COMPLETED_VALUE_OF_STATUS:
                setAllUserOrdersByOrderStatusToAttributes(OrderStatus.getCompletedStatus(), NO_ONE_COMPLETED_ORDER_MESSAGE, req);
                break;
            case SHIPPING_ORDERS_VALUE_OF_STATUS:
                setAllUserOrdersByOrderStatusToAttributes(OrderStatus.getShippingStatus(), NO_ONE_SHIPPING_ORDER_MESSAGE, req);
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
                    setErrorToAttributes(NO_ONE_SHIPPING_ORDER_MESSAGE, req);
                } catch (ServiceException e) {
                    throw new ActionException(e);
                }

                break;
            default:
                setErrorToAttributes(NO_ONE_ORDER_MESSAGE, req);
                break;
        }


        log.trace("get order action was finished");
        return FORM_NAME;
    }


    private void setAllUserOrdersByOrderStatusToAttributes(OrderStatus orderStatus, String errorMessage, HttpServletRequest req) throws ActionException {
        try {
            log.trace("trying to get all {} orders of user {}", orderStatus.getName(), user.getLogin());
            List<Order> userShippOrders = orderService.getUserOrdersByOrderStatus(user.getId(), orderStatus);
            req.setAttribute(ORDERS, userShippOrders);
            log.trace("got {} orders", userShippOrders.size());
        } catch (ServiceNoDataException e) {
            setErrorToAttributes(errorMessage, req);
        } catch (ServiceException e) {
            throw new ActionException(e);
        }
    }

    private void setErrorToAttributes(String errorMessage, HttpServletRequest req) {
        message.add(errorMessage);
        req.setAttribute(PRODUCT_MESSAGE_ATTRIBUTE, message);
        log.trace("no one orders");
    }


}
