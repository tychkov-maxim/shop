package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcDaoFactory;
import com.epam.tm.shop.exception.DaoException;
import com.epam.tm.shop.exception.JdbcException;

public abstract class DaoFactory implements AutoCloseable {

    public static DaoFactory createFactory() throws DaoException {
        try {
            return new JdbcDaoFactory();
        } catch (JdbcException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void close() throws DaoException {
    }

    public abstract UserDao getUserDao();

    public abstract ProductDao getProductDao();

    public abstract ProductCategoryDao getProductCategoryDao();

    public abstract CartDao getCartDao();

    public abstract OrderDao getOrderDao();

    public abstract void beginTx() throws DaoException;

    public abstract void commit() throws DaoException;

    public abstract void rollback() throws DaoException;

}
