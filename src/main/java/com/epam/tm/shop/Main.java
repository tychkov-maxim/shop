package com.epam.tm.shop;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.dao.jdbc.JdbcDaoFactory;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.text.MessageFormat;
import java.util.*;

public class Main {

    public static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        String url,username,password,driverName;
        int maxCon;

        try {
            PropertyManager manager = new PropertyManager("connection-pool.properties");
            url = manager.getPropertyKey("db.url");
            username = manager.getPropertyKey("db.username");
            password = manager.getPropertyKey("db.password");
            maxCon = manager.getIntPropertyKey("max.connections");
            driverName = manager.getPropertyKey("db.driverClassName");
            ConnectionPool pool = new ConnectionPool(url, username, password, maxCon,driverName);
            JdbcDaoFactory.setPool(pool);
        } catch (PropertyManagerException e) {
            log.error("getting properties was failed and connection pool was not created",e);
        } catch (PoolException e) {
            log.error("creating connection pool was failed",e);
        }


        try {
            ProductDao productDao = DaoFactory.createFactory().getProductDao();

            Product product = productDao.findById(3);



            CartService cartService = new CartService();
            Cart cart = cartService.getCartById(1);

            cart.addProduct(product,10);

            for (Map.Entry<Product, Integer> productIntegerEntry : cart.getCart().entrySet()) {
                System.out.println(productIntegerEntry.getKey() + " " + productIntegerEntry.getValue());
            }

        } catch (ServiceException e) {
            log.error("cart service exception",e);
        }


    }

}