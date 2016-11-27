package com.epam.tm.shop;

import com.epam.tm.shop.dao.DaoFactory;
import com.epam.tm.shop.dao.ProductDao;
import com.epam.tm.shop.dao.UserDao;
import com.epam.tm.shop.dao.jdbc.JdbcDao;
import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.dao.jdbc.JdbcUserDao;
import com.epam.tm.shop.entity.Product;
import com.epam.tm.shop.entity.Role;
import com.epam.tm.shop.entity.User;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.pool.PoolException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.Connection;
import java.util.*;

public class Main {
    public static void main(String[] args) throws PoolException {

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();



        DaoFactory factory = DaoFactory.createFactory();
        ProductDao productDao = factory.getProductDao();
        productDao.setCon(connection);



        try {
            Product product = productDao.findById(1);
            System.out.println(product);

        } catch (JdbcException e) {
            e.printStackTrace();
        }
    }


    class MyThread extends Thread {
        public MyThread() {

            start();
        }

        @Override
        public void run() {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                Connection connection = connectionPool.getConnection();
                System.out.println(connection);
                this.sleep(10000);
                connection.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}