package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.Cart;
import com.epam.tm.shop.entity.OrderStatus;

import java.sql.Connection;
import java.util.List;

public interface CartDao extends Dao<Cart> {
    List<Cart> findAllCartsByOrderStatus(OrderStatus orderStatus) throws DaoException, DaoNoDataException;
}
