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
import com.epam.tm.shop.validator.OnlyNumberValidator;
import com.epam.tm.shop.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.tm.shop.util.ConstantHolder.*;

public class ChangeOrderStatusAction implements Action {


    private static final Logger log = LoggerFactory.getLogger(ChangeOrderStatusAction.class);

    private static final String REDIRECT_COMPLETED = "redirect:/orders.do?status=completed";
    private static final String REDIRECT_PROCESSING = "redirect:/orders.do?status=all";
    private static final String ORDER_ID_PARAMETER = "order";
    private static final String CHANGED_STATUS_SUCCESSFULLY_LOGGER_MESSAGE = "order status was changed successfully";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        log.trace("start to change status action");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_SESSION_USER_NAME);

        Validator notEmptyParameterValidator = new NotEmptyParameterValidator();
        Validator onlyNumberValidator = new OnlyNumberValidator();
        String orderStatusParam = req.getParameter(STATUS_PARAMETER);
        String orderIdParam = req.getParameter(ORDER_ID_PARAMETER);


        if (notEmptyParameterValidator.isValid(orderStatusParam) && onlyNumberValidator.isValid(orderIdParam)) {
            int orderId = Integer.parseInt(orderIdParam);
            OrderService orderService = new OrderService();
            log.trace("try to change order status to {} in order {}", orderStatusParam, orderId);

            try {

                if (user.getRole().equals(Role.getAdministratorRole())) {
                    switch (orderStatusParam) {
                        case PROCESSING_ORDERS_VALUE_OF_STATUS:
                            orderService.changeOrderStatusById(orderId, OrderStatus.getProcessingStatus());
                            log.trace(CHANGED_STATUS_SUCCESSFULLY_LOGGER_MESSAGE);
                            break;
                        case SHIPPING_ORDERS_VALUE_OF_STATUS:
                            orderService.changeOrderStatusById(orderId, OrderStatus.getShippingStatus());
                            log.trace(CHANGED_STATUS_SUCCESSFULLY_LOGGER_MESSAGE);
                            return REDIRECT_PROCESSING;
                        case COMPLETED_VALUE_OF_STATUS:
                            orderService.changeOrderStatusById(orderId, OrderStatus.getCompletedStatus());
                            log.trace(CHANGED_STATUS_SUCCESSFULLY_LOGGER_MESSAGE);
                            break;
                    }
                }

                if (user.getRole().equals(Role.getUserRole())) {
                    if (orderStatusParam.equals(COMPLETED_VALUE_OF_STATUS)) {
                        Order order = orderService.getOrderById(orderId);
                        if (order.getStatus().equals(OrderStatus.getShippingStatus()))
                            if (user.equals(order.getUser())) {
                                orderService.changeOrderStatusById(orderId, OrderStatus.getCompletedStatus());
                                log.trace(CHANGED_STATUS_SUCCESSFULLY_LOGGER_MESSAGE);
                            }
                    }
                }

            } catch (ServiceException e) {
                throw new ActionException(e);
            } catch (ServiceNoDataException e) {
                return REDIRECT_COMPLETED;
            }
        }
        return REDIRECT_COMPLETED;
    }
}
