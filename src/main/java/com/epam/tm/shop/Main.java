package com.epam.tm.shop;

import com.epam.tm.shop.dao.*;
import com.epam.tm.shop.dao.jdbc.JdbcDaoFactory;
import com.epam.tm.shop.entity.*;
import com.epam.tm.shop.pool.ConnectionPool;
import com.epam.tm.shop.pool.PoolException;
import com.epam.tm.shop.service.*;
import com.epam.tm.shop.util.PropertyManager;
import com.epam.tm.shop.util.PropertyManagerException;
import com.epam.tm.shop.validator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Main {



    public static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws ValidatorException, ServiceException, DaoException, DaoNoDataException, DaoNonUniqueFieldException, ServiceNoDataException {
        String url, username, password, driverName;
        int maxCon;

        try {
            int i = 0;//just fo fan
            PropertyManager manager = new PropertyManager("connection-pool.properties");
            url = manager.getPropertyKey("db.url");
            username = manager.getPropertyKey("db.username");
            password = manager.getPropertyKey("db.password");
            maxCon = manager.getIntPropertyKey("max.connections");
            driverName = manager.getPropertyKey("db.driverClassName");
            ConnectionPool pool = new ConnectionPool(url, username, password, maxCon, driverName);
            JdbcDaoFactory.setPool(pool);
        } catch (PropertyManagerException e) {
            log.error("getting properties was failed and connection pool was not created", e);
        } catch (PoolException e) {
            log.error("creating connection pool was failed", e);
        }

        OrderService orderService = new OrderService();

        List<Order> ordersByOrderStatus = orderService.getAllOrdersByOrderStatus(OrderStatus.getProcessingStatus());

        for (Order ordersByOrderStatu : ordersByOrderStatus) {
            System.out.println("==========================");
            System.out.println(ordersByOrderStatu);
            System.out.println("==========================");
        }

    }


}