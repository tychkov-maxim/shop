package com.epam.tm.shop;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.*;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.pool.PoolException;
import com.epam.tm.shop.service.CartService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.util.*;

public class Main {
    public static void main(String[] args) throws PoolException {

        DaoFactory factory = DaoFactory.createFactory();
        OrderDao orderDao = factory.getOrderDao();
        UserDao userDao = factory.getUserDao();
        ProductDao productDao = factory.getProductDao();
        CartDao cartDao = factory.getCartDao();


        try {
            User user = userDao.findById(1);
            Product product = productDao.findById(3);

            Cart cart = new Cart();
            cart.addProduct(product,4);

            Order order = new Order(cart,user, DateTime.now(), Money.of(CurrencyUnit.USD, product.getPrice().getAmount()), OrderStatus.getProcessingStatus());
            order = orderDao.save(order);

            cartDao.insert(cart,order.getId());

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