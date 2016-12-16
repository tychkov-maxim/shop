package com.epam.tm.shop;

import com.epam.tm.shop.util.PropertyManager;
import com.epam.tm.shop.util.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Main {

    public static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws PropertyManagerException {
/*        String url,username,password,driverName;
        int maxCon;

        try {
            int i = 0;//just fo fan
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

        ProductService service = new ProductService();

        try {
            List<Product> tv = service.getProductsWithPagination(0,50);
            System.out.println(tv.get(0));
        } catch (ServiceException e) {
            e.printStackTrace();
        }*/


        PropertyManager propertyManager = new PropertyManager("validator.properties");
        Enumeration<String> propertyNames = (Enumeration<String>) propertyManager.getPropertyNames();

        List<String> list = new ArrayList<>();
        while(propertyNames.hasMoreElements())
            list.add(propertyNames.nextElement());

        for (String s : list) {
            s.matches("")
        }

    }

}