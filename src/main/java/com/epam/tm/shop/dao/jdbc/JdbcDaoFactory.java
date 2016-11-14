package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.DaoFactory;
import com.epam.tm.shop.dao.ProductDao;
import com.epam.tm.shop.dao.UserDao;

import java.sql.Connection;

public class JdbcDaoFactory extends DaoFactory{

    private Connection connection;

    @Override
    public UserDao getUserDao() {
        return new JdbcUserDao(connection);
    }

    @Override
    public ProductDao getProductDao() {
        return null;
    }
}
