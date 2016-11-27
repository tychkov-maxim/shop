package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.Cart;

import java.sql.Connection;

public interface CartDao extends Dao<Cart> {
    void setCon(Connection con);// FIXME: 22.11.2016
    Cart insert(Cart cart, int id) throws JdbcException;
}
