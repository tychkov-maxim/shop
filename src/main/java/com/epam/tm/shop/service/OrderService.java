package com.epam.tm.shop.service;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.dao.jdbc.JdbcNonUniqueFieldException;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.Order;
import com.epam.tm.shop.entity.User;

public class OrderService {


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

    public Order makeOrder(Order order) throws ServiceException, ServiceNonUniqueFieldException {
        try (DaoFactory factory = DaoFactory.createFactory()) {
            OrderDao orderDao = factory.getOrderDao();
            return orderDao.save(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (DaoNonUniqueFieldException e) {
            throw new ServiceNonUniqueFieldException(e);
        }
    }
}