package com.epam.tm.shop.dao;

import com.epam.tm.shop.entity.Order;
import com.epam.tm.shop.entity.OrderStatus;

import java.util.List;

public interface OrderDao extends Dao<Order> {
    List<Order> findAllOrdersByStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException;
    List<Order> findUserOrdersByStatus(int userId, OrderStatus orderStatus) throws DaoException, DaoNoDataException;
}
