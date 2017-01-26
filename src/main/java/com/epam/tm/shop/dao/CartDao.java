package com.epam.tm.shop.dao;

import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.OrderStatus;
import com.epam.tm.shop.exception.DaoException;
import com.epam.tm.shop.exception.DaoNoDataException;

import java.util.List;

public interface CartDao extends Dao<Cart> {
    List<Cart> findAllCartsByOrderStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException;

    List<Cart> findUserCartsByOrderStatus(int userId, OrderStatus orderStatus) throws DaoException, DaoNoDataException;
}
