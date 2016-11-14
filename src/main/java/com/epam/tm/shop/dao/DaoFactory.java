package com.epam.tm.shop.dao;

import com.epam.tm.shop.dao.jdbc.JdbcDaoFactory;

public abstract class DaoFactory {


    public static DaoFactory createFactory(){
        return new JdbcDaoFactory();
    }

    public abstract UserDao getUserDao();
    public abstract ProductDao getProductDao();

}
