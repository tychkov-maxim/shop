package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.Cart;

import java.sql.Connection;

public interface CartDao extends Dao<Cart> {
    Cart insert(Cart cart, int id) throws DaoException;
    Cart update(Cart cart) throws DaoException;
}
