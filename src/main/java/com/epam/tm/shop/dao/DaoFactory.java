package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcDaoFactory;
import com.epam.tm.shop.pool.ConnectionPool;

public abstract class DaoFactory implements AutoCloseable{

    private static ConnectionPool pool;

    public static DaoFactory createFactory() throws DaoException {
        try {
            return new JdbcDaoFactory(pool);
        } catch (Exception e) {
           throw new DaoException(e);
        }
    }

    public static void setPool(ConnectionPool pool) {
        DaoFactory.pool = pool;
    }

    @Override
    public void close() throws DaoException {
    }

    public abstract UserDao getUserDao();
    public abstract ProductDao getProductDao();
    public abstract ProductCategoryDao getProductCategoryDao();
    public abstract CartDao getCartDao();
    public abstract OrderDao getOrderDao();

}
