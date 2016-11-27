package com.epam.tm.shop;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.dao.jdbc.JdbcDao;
import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.dao.jdbc.JdbcUserDao;
import com.epam.tm.shop.entity.*;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.pool.PoolException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.Connection;
import java.util.*;

public class Main {
    public static void main(String[] args) throws PoolException {

        Cart cart = new Cart();
        Product product = new Product();
        product.setId(555);
        product.setQuantity(100);
        System.out.println(cart.addProduct(product, 55));
        product = new Product();
        product.setId(666);
        product.setQuantity(100);
        System.out.println(cart.addProduct(product, 55));
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();



        DaoFactory factory = DaoFactory.createFactory();
        CartDao cartDao = factory.getCartDao();
        cartDao.setCon(connection);



        try {

            cart = cartDao.insert(cart, 1);

            System.out.println(cart);

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