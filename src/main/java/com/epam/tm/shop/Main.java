package com.epam.tm.shop;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.*;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.pool.PoolException;
import com.epam.tm.shop.service.CartService;

import java.sql.Connection;
import java.util.*;

public class Main {
    public static void main(String[] args) throws PoolException {

        DaoFactory factory = DaoFactory.createFactory();
        CartDao cartDao = factory.getCartDao();

        CartService cartService = new CartService();

        try {

            Cart cart = cartService.getCartById(1);
            for (Map.Entry<Product, Integer> entry : cart.getCart().entrySet()) {
                System.out.println(entry);
            }


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