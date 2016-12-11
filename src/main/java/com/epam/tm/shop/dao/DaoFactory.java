package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcDaoFactory;
import com.epam.tm.shop.pool.ConnectionPool;

public abstract class DaoFactory {

    private static ConnectionPool pool;

    public static DaoFactory createFactory(){
        return new JdbcDaoFactory(pool);
    }

    public static void setPool(ConnectionPool pool) {
        DaoFactory.pool = pool;
    }

    public abstract UserDao getUserDao();
    public abstract ProductDao getProductDao();
    public abstract ProductCategoryDao getProductCategoryDao();
    public abstract CartDao getCartDao();
    public abstract OrderDao getOrderDao();

}
