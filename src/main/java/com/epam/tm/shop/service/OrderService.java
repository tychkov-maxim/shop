package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.dao.jdbc.JdbcNonUniqueFieldException;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.Order;
import com.epam.tm.shop.entity.User;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderService {

    public static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private static final String NOT_ENOUGH_MONEY_ERROR = "not.enough.money.error.message";

    public Order getOrderById(int id) throws ServiceException, ServiceNoDataException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            OrderDao orderDao = factory.getOrderDao();
            Order order = orderDao.findById(id);

            CartService cartService = new CartService();
            Cart cart = cartService.getCartById(order.getCart().getId());
            order.setCart(cart);

            UserService userService = new UserService();
            User user = userService.getUserById(order.getUser().getId());
            order.setUser(user);

            return order;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNoDataException e) {
            throw new ServiceNoDataException(e);
        }
    }

    public Order makeOrder(Order order) throws ServiceException, ServiceNonUniqueFieldException, ServiceExceptionError {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            factory.beginTx();

            OrderDao orderDao = factory.getOrderDao();
            UserDao userDao = factory.getUserDao();
            CartDao cartDao = factory.getCartDao();

            User user = userDao.findById(order.getUser().getId());
            Money userAccount = user.getAccount();
            if (userAccount.isLessThan(order.getTotal())){
                throw new ServiceExceptionError(NOT_ENOUGH_MONEY_ERROR);
            } else {
                Money balance = userAccount.minus(order.getTotal());
                user.setAccount(balance);
            }

            Cart savedCart = cartDao.save(order.getCart());
            order.setCart(savedCart);
            userDao.save(user);
            Order saveOrder = orderDao.save(order);
            factory.commit();

            return saveOrder;
        } catch (DaoException | DaoNoDataException e) {
            throw new ServiceException(e);
        } catch (DaoNonUniqueFieldException e) {
            throw new ServiceNonUniqueFieldException(e);
        }
    }
}