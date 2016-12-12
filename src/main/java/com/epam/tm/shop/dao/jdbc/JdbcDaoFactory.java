package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.pool.PoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class JdbcDaoFactory extends DaoFactory{

    public static final Logger log = LoggerFactory.getLogger(JdbcDaoFactory.class);

    private Connection connection;

    public JdbcDaoFactory(ConnectionPool pool) throws JdbcException {
        try {
            connection = pool.getConnection();
        } catch (PoolException e) {
            throw new JdbcException("Can't get connection from connection pool",e);
        }
    }

    @Override
    public UserDao getUserDao() {
        return new JdbcUserDao(connection);
    }

    @Override
    public ProductDao getProductDao() {
        return new JdbcProductDao(connection);
    }
    @Override
    public ProductCategoryDao getProductCategoryDao() {
        return new JdbcProductCategory(connection);
    }

    @Override
    public CartDao getCartDao() {
        return new JdbcCartDao(connection);
    }

    @Override
    public OrderDao getOrderDao() {
        return new JdbcOrderDao(connection);
    }
}
