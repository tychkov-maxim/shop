package com.epam.tm.shop;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.dao.jdbc.JdbcException;
import com.epam.tm.shop.entity.*;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.pool.PoolException;
import com.epam.tm.shop.service.CartService;
import com.epam.tm.shop.service.ServiceException;
import com.epam.tm.shop.util.PropertyManager;
import com.epam.tm.shop.util.PropertyManagerException;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.util.*;

public class Main {
    public static void main(String[] args) throws PropertyManagerException {

/*        ConnectionPool pool = new ConnectionPool();
        DaoFactory.setPool(pool);

        DaoFactory factory = DaoFactory.createFactory();
        OrderDao orderDao = factory.getOrderDao();
        UserDao userDao = factory.getUserDao();
        ProductDao productDao = factory.getProductDao();
        CartDao cartDao = factory.getCartDao();

        CartService cartService = new CartService();

        try {
            Cart cart = cartService.getCartById(1);

            System.out.println(cart);
        } catch (ServiceException e) {
            e.printStackTrace();
        }*/


            PropertyManager propertyManager = new PropertyManager("1.properties");
            propertyManager.getPropertyKey("hello");

    }

}