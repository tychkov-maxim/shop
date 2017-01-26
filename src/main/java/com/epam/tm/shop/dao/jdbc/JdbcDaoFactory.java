package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.exception.JdbcException;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.exception.PoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory extends DaoFactory {

    private static final Logger log = LoggerFactory.getLogger(JdbcDaoFactory.class);
    private static ConnectionPool pool;
    private Connection connection;

    public JdbcDaoFactory() throws JdbcException {
        try {
            if (pool == null) {
                throw new JdbcException("pool wasn't set");
            }
            connection = pool.getConnection();
        } catch (PoolException e) {
            throw new JdbcException("can't get connection from connection pool", e);
        }
    }

    public static ConnectionPool getPool() {
        return JdbcDaoFactory.pool;
    }

    public static void setPool(ConnectionPool pool) {
        JdbcDaoFactory.pool = pool;
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

    @Override
    public void beginTx() throws JdbcException {
        try {
            connection.setAutoCommit(false);
            log.trace("transaction was started");
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    @Override
    public void commit() throws JdbcException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            log.trace("transaction was commit");
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    @Override
    public void rollback() throws JdbcException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            log.trace("transaction was rollback");
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    @Override
    public void close() throws JdbcException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }
}
